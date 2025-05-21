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

package config

// Options Configuration options
type Options struct {
	// not required
	AgentName string // Customized agent name, the host name is used by default
	// required
	AccessToken string // AccessToken
	// required
	ServerUrl string // Server address, default is localhost:8080
	// required
	Cron string // The default trigger period for scheduled tasks is every four hours.
	// required
	RunOnlyOnce bool // Whether to run only once. By default, it only runs once. If you need to run multiple times, please configure it to false
	// not required
	Sites []string // Deployment site. If the deployment site is configured as ['H1'], only cloudAccount of this site can be obtained.
	// not required
	AttentionErrorTexts []string //Pay attention to the risk error information. If the error message contains text, the risk will be submitted to the server.
}

var (
	defaultServerUrl = "http://localhost:8080"
	defaultCron      = "@every 4h"
)
