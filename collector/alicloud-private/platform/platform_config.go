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

package platform

import (
	"github.com/alicloud-sqa/collector"
	ecs "github.com/alicloud-sqa/collector/ecsv1"
	"github.com/alicloud-sqa/collector/loadbalance/slb"
	"github.com/alicloud-sqa/collector/oss"
	"github.com/alicloud-sqa/collector/vpc"
	"github.com/alicloud-sqa/collector/vpc/eip"
	"github.com/alicloud-sqa/collector/vpc/nat"
	"github.com/core-sdk/constant"
	"github.com/core-sdk/schema"
)

func GetPlatformConfig() *schema.Platform {
	regions := []string{
		"cn-hangzhou"}

	return schema.GetInstance(schema.PlatformConfig{
		Name: string(constant.AlibabaCloudPrivate),
		Resources: []schema.Resource{
			ecs.GetEcsData(),
			ecs.GetSecurityGroupData(),
			slb.GetSLBResource(),
			oss.GetOSSResource(),
			vpc.GetVPCResource(),
			eip.GetEIPResource(),
			nat.GetNatResource(),
		},

		Service:        &collector.Services{},
		DefaultRegions: regions,
	})
}
