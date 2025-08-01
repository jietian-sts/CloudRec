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
	"github.com/core-sdk/log"
	"github.com/robfig/cron/v3"
	"time"
)

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
					if available > 0 {
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
										// Use priority queue for task accounts to process them first
										added := e.accountQueue.AddPriorityAccounts(taskAccounts)
										log.GetWLogger().Info(fmt.Sprintf("Added %d priority task accounts to front of queue", added))

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
