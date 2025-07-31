// Licensed to the Apache Software Foundation (ASF) under one or more
// contributor license agreements.  See the NOTICE file distributed with
// this work for additional information regarding copyright ownership.
// The ASF licenses this file to You under the Apache License, Version 2.0
// (the "License"); you may not use this file except in compliance with
// the License.  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package schema

import (
	"fmt"
	"sync"
	"time"

	"github.com/core-sdk/log"
	"github.com/robfig/cron/v3"
)

// TaskQueue manages task execution queue
type TaskQueue struct {
	tasks   chan func()
	workers int
	mu      sync.Mutex
	running bool
}

// NewTaskQueue creates a new task queue with specified number of workers
func NewTaskQueue(workers int) *TaskQueue {
	return &TaskQueue{
		tasks:   make(chan func(), 100), // buffered channel for 100 tasks
		workers: workers,
	}
}

// Start starts the task queue workers
func (tq *TaskQueue) Start() {
	tq.mu.Lock()
	defer tq.mu.Unlock()

	if tq.running {
		return
	}
	tq.running = true

	for i := 0; i < tq.workers; i++ {
		go tq.worker()
	}
}

// Stop stops the task queue
func (tq *TaskQueue) Stop() {
	tq.mu.Lock()
	defer tq.mu.Unlock()

	if !tq.running {
		return
	}
	tq.running = false
	close(tq.tasks)
}

// AddTask adds a task to the queue
func (tq *TaskQueue) AddTask(task func()) {
	tq.mu.Lock()
	running := tq.running
	tq.mu.Unlock()

	if !running {
		return
	}

	select {
	case tq.tasks <- task:
		// Task added successfully
	default:
		log.GetWLogger().Warn("Task queue is full, dropping task")
	}
}

// worker processes tasks from the queue
func (tq *TaskQueue) worker() {
	for task := range tq.tasks {
		func() {
			defer func() {
				if r := recover(); r != nil {
					log.GetWLogger().Error(fmt.Sprintf("Task panicked: %v", r))
				}
			}()
			task()
		}()
	}
}

// AccountQueue manages cloud account processing queue
type AccountQueue struct {
	accounts   []CloudAccount
	maxSize    int
	mu         sync.RWMutex
	processing map[string]bool // track which accounts are being processed
	executor   *Executor
	taskQueue  *TaskQueue
}

// NewAccountQueue creates a new account queue with specified max size
func NewAccountQueue(maxSize int, executor *Executor) *AccountQueue {
	return &AccountQueue{
		accounts:   make([]CloudAccount, 0, maxSize),
		maxSize:    maxSize,
		processing: make(map[string]bool),
		executor:   executor,
		taskQueue:  NewTaskQueue(executor.platform.CloudAccountMaxConcurrent), // default 4 concurrent workers
	}
}

// Start starts the account queue processing
func (aq *AccountQueue) Start() {
	aq.taskQueue.Start()
}

// Stop stops the account queue processing
func (aq *AccountQueue) Stop() {
	aq.taskQueue.Stop()
}

// GetAvailableSlots returns the number of available slots in the queue
func (aq *AccountQueue) GetAvailableSlots() int {
	aq.mu.RLock()
	defer aq.mu.RUnlock()
	return aq.getAvailableSlotsLocked()
}

// getAvailableSlotsLocked returns available slots without acquiring lock (internal use)
func (aq *AccountQueue) getAvailableSlotsLocked() int {
	processingCount := len(aq.processing)
	queuedCount := len(aq.accounts)
	totalUsed := processingCount + queuedCount

	if totalUsed >= aq.maxSize {
		return 0
	}
	return aq.maxSize - totalUsed
}

// AddAccounts adds new accounts to the queue
func (aq *AccountQueue) AddAccounts(accounts []CloudAccount) int {
	aq.mu.Lock()
	defer aq.mu.Unlock()

	availableSlots := aq.getAvailableSlotsLocked()
	if availableSlots <= 0 {
		return 0
	}

	added := 0
	for _, account := range accounts {
		if added >= availableSlots {
			break
		}
		// Check if account is already in queue or being processed
		if !aq.isAccountInQueue(account.CloudAccountId) && !aq.processing[account.CloudAccountId] {
			aq.accounts = append(aq.accounts, account)
			added++
		}
	}

	return added
}

// ProcessNext processes the next account in the queue
func (aq *AccountQueue) ProcessNext() bool {
	aq.mu.Lock()
	if len(aq.accounts) == 0 {
		aq.mu.Unlock()
		return false
	}

	account := aq.accounts[0]
	aq.accounts = aq.accounts[1:]
	aq.processing[account.CloudAccountId] = true
	aq.mu.Unlock()

	// Add task to process this account
	aq.taskQueue.AddTask(func() {
		aq.processAccount(account)
	})

	return true
}

// processAccount processes a single cloud account
func (aq *AccountQueue) processAccount(account CloudAccount) {
	defer func() {
		aq.mu.Lock()
		delete(aq.processing, account.CloudAccountId)
		aq.mu.Unlock()

		// Try to process next account
		aq.ProcessNext()
	}()

	log.GetWLogger().Info(fmt.Sprintf("Processing cloud account: %s", account.CloudAccountId))

	param := CollectorParam{
		registered:     aq.executor.registered,
		CloudRecLogger: aq.executor.cloudRecLogger,
		accounts:       []CloudAccount{account},
	}

	err := aq.executor.platform.CollectorV3(param)
	if err != nil {
		log.GetWLogger().Error(fmt.Sprintf("Error processing account %s: %v", account.CloudAccountId, err))
	} else {
		log.GetWLogger().Info(fmt.Sprintf("Successfully processed account: %s", account.CloudAccountId))
	}
}

// isAccountInQueue checks if an account is already in the queue
func (aq *AccountQueue) isAccountInQueue(accountId string) bool {
	for _, account := range aq.accounts {
		if account.CloudAccountId == accountId {
			return true
		}
	}
	return false
}

// GetQueueStatus returns current queue status
func (aq *AccountQueue) GetQueueStatus() (queued int, processing int, available int) {
	aq.mu.RLock()
	defer aq.mu.RUnlock()

	return len(aq.accounts), len(aq.processing), aq.getAvailableSlotsLocked()
}

// loadCloudAccounts Load the cloud account and decrypt it, and at the same time put the local test account into the result
func (e *Executor) loadCloudAccounts(taskIds []int64) []CloudAccount {
	return e.loadCloudAccountsWithCount(taskIds, 0)
}

// loadCloudAccountsWithCount Load specified number of cloud accounts from server
func (e *Executor) loadCloudAccountsWithCount(taskIds []int64, count int) []CloudAccount {
	if !e.registered {
		return e.platform.DefaultCloudAccounts
	}

	var cloudAccountList []CloudAccount
	var err error

	if count > 0 {
		cloudAccountList, err = e.platform.client.LoadAccountFromServerWithCount(e.registry.RegistryValue, taskIds, count)
	} else {
		cloudAccountList, err = e.platform.client.LoadAccountFromServer(e.registry.RegistryValue, taskIds)
	}

	if err != nil {
		log.GetWLogger().Warn(fmt.Sprintf("Failed to get account from server: %v", err))
		return nil
	}
	cloudAccountList = decryptCredentialsInfo(cloudAccountList, e.registry.SecretKey)
	return cloudAccountList
}

func (e *Executor) listCollectorTask() (tasks []TaskResp, err error) {
	tasks, err = e.platform.client.ListCollectorTask(e.registry.RegistryValue)
	return
}

func (e *Executor) Start() (err error) {
	if e.registered {
		time.Sleep(2 * time.Second)
		e.SendSupportResourceType()
	}

	if !e.opts.RunOnlyOnce {
		// Start account queue processing
		e.accountQueue.Start()

		// Create a new cron instance
		// Recover from panics
		c := cron.New(cron.WithChain(
			cron.Recover(cron.DefaultLogger),
		))

		if e.registered {
			// Regular inspection tasks and queue management
			go func() {
				for {
					time.Sleep(15 * time.Second)
					// Handle specific tasks
					queued, processing, available := e.accountQueue.GetQueueStatus()
					log.GetWLogger().Info(fmt.Sprintf("Queue Status - Queued: %d, Processing: %d, Available: %d", queued, processing, available))
					if e.registered && available > 0 {
						tasks, taskErr := e.listCollectorTask()
						if taskErr != nil {
							log.GetWLogger().Warn(fmt.Sprintf("Failed to get task from server: %v", taskErr))
						} else if len(tasks) > 0 {
							// find task and match task type
							for _, task := range tasks {
								currentTask := task
								switch currentTask.TaskType {
								case collect:
									// Add task accounts to queue
									taskAccounts := matchTaskId(e.loadCloudAccounts(queryTaskIds(currentTask.TaskParams)), currentTask)
									if len(taskAccounts) > 0 {
										added := e.accountQueue.AddAccounts(taskAccounts)
										log.GetWLogger().Info(fmt.Sprintf("Added %d task accounts to queue", added))

										// Start processing task accounts
										for i := 0; i < added; i++ {
											e.accountQueue.ProcessNext()
										}
									}
									// TODO other task type
								}
							}
						}
					}
				}
			}()
		}

		if e.registered {
			// Add a task for regular queue status monitoring
			_, err = c.AddFunc(e.opts.Cron, func() {
				queued, processing, available := e.accountQueue.GetQueueStatus()
				log.GetWLogger().Info(fmt.Sprintf("Queue Status - Queued: %d, Processing: %d, Available: %d", queued, processing, available))

				// Load more accounts if queue has space
				if available > 0 {
					newAccounts := e.loadCloudAccountsWithCount(nil, available)
					if len(newAccounts) > 0 {
						added := e.accountQueue.AddAccounts(newAccounts)
						log.GetWLogger().Info(fmt.Sprintf("Cron job added %d accounts to queue", added))

						// Start processing new accounts
						for i := 0; i < added; i++ {
							e.accountQueue.ProcessNext()
						}
					}
				}
			})
		}

		if err != nil {
			log.GetWLogger().Info(fmt.Sprintf("Error adding job: %v", err))
			return
		}

		// Initial load of accounts to queue
		go func() {
			initialAccounts := e.loadCloudAccounts(nil)
			if len(initialAccounts) > 0 {
				added := e.accountQueue.AddAccounts(initialAccounts)
				log.GetWLogger().Info(fmt.Sprintf("Initial load: added %d accounts to queue", added))

				// Start processing initial accounts
				for i := 0; i < added; i++ {
					e.accountQueue.ProcessNext()
				}
			}
		}()

		log.GetWLogger().Info("———————— RESOURCE COLLECT AGENT START SUCCESSFULLY ————————")
		c.Start()

		select {}
	} else {
		accounts := e.loadCloudAccounts(nil)
		param := CollectorParam{
			registered:     e.registered,
			CloudRecLogger: e.cloudRecLogger,
			accounts:       accounts,
		}
		err = e.platform.CollectorV3(param)
		log.GetWLogger().Info("———————— RUN ONCE DONE ————————")
		return err
	}
}
