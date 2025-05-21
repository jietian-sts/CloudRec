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
	"sync"
	"time"
)

// JobStatus to keep track of job state
type JobStatus struct {
	running bool
	mu      sync.Mutex
}

func (e *Executor) Start() (err error) {
	if !e.opts.RunOnlyOnce {
		// Create a new cron instance
		// Recover from panics
		c := cron.New(cron.WithChain(
			cron.Recover(cron.DefaultLogger),
		))

		jobStatus := &JobStatus{}

		// Add a task
		_, err = c.AddFunc(e.opts.Cron, func() {
			e.runJob(jobStatus)
		})
		if err != nil {
			log.GetWLogger().Info(fmt.Sprintf("Error adding job: %v", err))
			return
		}

		// Immediately trigger the job once on startup
		go e.runJob(jobStatus)

		log.GetWLogger().Info(fmt.Sprintf("———————— RESOURCE COLLECT AGENT START SUCCESSFULLY ————————"))
		c.Start()

		select {}
	} else {
		var cloudAccountList []CloudAccount
		if e.registered {
			time.Sleep(4 * time.Second)
			e.SendSupportResourceType()
			cloudAccountList = e.platform.client.LoadAccountFromServer(e.registry.RegistryValue)
			cloudAccountList = decryptCredentialsInfo(cloudAccountList, e.registry.SecretKey)
		}
		cloudAccountList = append(cloudAccountList, e.platform.DefaultCloudAccounts...)
		e.platform.CloudAccounts = cloudAccountList

		param := CollectorParam{
			registered:     e.registered,
			CloudRecLogger: e.cloudRecLogger,
		}
		err = e.platform.CollectorV3(param)
		log.GetWLogger().Info(fmt.Sprintf("———————— RUN ONCE DONE ————————"))
		return err
	}
}

// runJob executes the job, ensuring only one instance runs at a time
func (e *Executor) runJob(status *JobStatus) {
	status.mu.Lock()
	if status.running {
		status.mu.Unlock()
		log.GetWLogger().Warn("Previous job is still running, skipping this execution.")
		return
	}
	status.running = true
	status.mu.Unlock()

	defer func() {
		if r := recover(); r != nil {
			log.GetWLogger().Error(fmt.Sprintf("Job panicked: %v", r))
		}
		status.mu.Lock()
		status.running = false
		status.mu.Unlock()
		jobCompleted()
	}()
	var cloudAccountList []CloudAccount
	if e.registered {
		time.Sleep(4 * time.Second)
		e.SendSupportResourceType()
		cloudAccountList = e.platform.client.LoadAccountFromServer(e.registry.RegistryValue)
		cloudAccountList = decryptCredentialsInfo(cloudAccountList, e.registry.SecretKey)
	}

	cloudAccountList = append(cloudAccountList, e.platform.DefaultCloudAccounts...)
	e.platform.CloudAccounts = cloudAccountList

	param := CollectorParam{
		registered:     e.registered,
		CloudRecLogger: e.cloudRecLogger,
	}
	err := e.platform.CollectorV3(param)
	log.GetWLogger().Warn(fmt.Sprintf("run job completed, err: %v", err))
}

// todo
// jobCompleted sends a notification that the job is done
func jobCompleted() {
	//log.GetWLogger().Info("Job has completed and notification has been sent.")
	// Notify via any desired method (e.g., channels, email, etc.)
	// Here, we'll just log for simplicity\n	logger.Println("Job has completed and notification has been sent.")\n}\n```
}
