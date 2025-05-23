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

package main

import (
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	"template/collector"
	"template/collector/resourcename"
)

func main() {
	p := schema.GetInstance(schema.PlatformConfig{
		// [6.1] ADD_NEW_CLOUD : Change the cloud provider here.
		//Name: string(constant.My_Cloud_Provider),
		Resources: []schema.Resource{
			// [6.2] ADD_NEW_CLOUD : Invoke the collect function you've implemented
			resourcename.GetSomeResource(),
		},

		Service:        &collector.Services{},
		DefaultRegions: []string{"cn-hangzhou"},
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}
