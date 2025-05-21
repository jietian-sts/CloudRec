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

package resource_type_name

import (
	"github.com/core-sdk/constant"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	"os"
	"test/collector"
	"testing"
)

var (
	TestAccount1 = schema.CloudAccount{
		CloudAccountId: "bbbbbbbbbbbbbbbbb",
		CommonCloudAccountAuthParam: schema.CommonCloudAccountAuthParam{
			AK: os.Getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"),
			SK: os.Getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"),
		},
	}
	DefaultRegions = []string{"cn-hangzhou"}
)

// Simulate the method of obtaining data
func TestGetSomeResource(t *testing.T) {
	p := schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.AlibabaCloud),
		Resources: []schema.Resource{
			GetSomeResource(),
		},

		Service:              &collector.Services{},
		DefaultRegions:       DefaultRegions,
		DefaultCloudAccounts: []schema.CloudAccount{TestAccount1},
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}

func TestGetSomeResourceTimeOut(t *testing.T) {
	p := schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.AlibabaCloud),
		Resources: []schema.Resource{
			GetSomeResourceTimeOut(),
		},

		Service:              &collector.Services{},
		DefaultRegions:       DefaultRegions,
		DefaultCloudAccounts: []schema.CloudAccount{TestAccount1},
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}

// Simulate the method of obtaining data and panic occurs
func TestGetSomeResourcePanic(t *testing.T) {
	p := schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.AlibabaCloud),
		Resources: []schema.Resource{
			GetSomeResourceWithPanic(),
		},

		Service:              &collector.Services{},
		DefaultRegions:       DefaultRegions,
		DefaultCloudAccounts: []schema.CloudAccount{TestAccount1},
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}

// TestNPE nil pointer exception
func TestNPE(t *testing.T) {

	p := schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.AlibabaCloud),
		Resources: []schema.Resource{
			GetNPE(),
		},

		Service:              &collector.Services{},
		DefaultRegions:       []string{"cn-hangzhou-not-exist"},
		DefaultCloudAccounts: []schema.CloudAccount{TestAccount1},
	})

	if err := schema.RunExecutor(p); err != nil {
		log.GetWLogger().Error(err.Error())
	}
}
