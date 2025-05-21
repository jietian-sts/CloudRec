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
	"context"
	"fmt"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	"strconv"
	"time"
)

// GetSomeResource Simulate the method of obtaining data, normal
func GetSomeResource() schema.Resource {
	return schema.Resource{
		ResourceType:      "ResourceType",
		ResourceTypeName:  "ResourceTypeName",
		ResourceGroupType: "ResourceGroupType",
		Desc:              "www.aliyun.com",
		ResourceDetailFunc: func(_ context.Context, service schema.ServiceInterface, res chan<- any) error {
			time.Sleep(3 * time.Second)
			log.GetWLogger().Info("AAAAAAAA 资产开始采集")
			for i := 0; i < 10; i++ {
				time.Sleep(1 * time.Second)
				res <- i
			}

			return nil

		},
		RowField: schema.RowField{
			ResourceId: "$.ResourceId",
		},
		Dimension: schema.Regional,
	}
}

// GetSomeResourceTimeOut Simulate the method of obtaining data and the timeout scenario
func GetSomeResourceTimeOut() schema.Resource {
	return schema.Resource{
		ResourceType:      "ResourceType",
		ResourceTypeName:  "ResourceTypeName",
		ResourceGroupType: "ResourceGroupType",
		Desc:              "www.aliyun.com",
		ResourceDetailFunc: func(_ context.Context, service schema.ServiceInterface, res chan<- any) error {
			time.Sleep(10 * time.Second)
			log.GetWLogger().Info("BBBBBBBB 资产开始采集")
			for i := 0; i < 10; i++ {
				time.Sleep(1 * time.Second)

				res <- strconv.Itoa(i) + "str"
			}

			return nil
		},
		RowField: schema.RowField{
			ResourceId: "$.ResourceId",
		},
		Dimension: schema.Regional,
	}
}

func GetSomeResourceWithPanic() schema.Resource {
	return schema.Resource{
		ResourceType:      "ResourceType",
		ResourceTypeName:  "ResourceTypeName",
		ResourceGroupType: "ResourceGroupType",
		Desc:              "www.aliyun.com",
		ResourceDetailFunc: func(_ context.Context, service schema.ServiceInterface, res chan<- any) error {
			//a := service.(*Services).A
			panic("panicpanicpanicpanicpanic")

			time.Sleep(3 * time.Second)
			log.GetWLogger().Info("CCCCCCC 资产开始采集")
			for i := 0; i < 10; i++ {
				time.Sleep(1 * time.Second)

				res <- strconv.Itoa(i) + "CCCCCC"
			}

			return nil
		},
		RowField: schema.RowField{
			ResourceId: "$.ResourceId",
		},
		Dimension: schema.Regional,
	}
}

func GetNPE() schema.Resource {
	return schema.Resource{
		ResourceType:      "ResourceType",
		ResourceTypeName:  "ResourceTypeName",
		ResourceGroupType: "ResourceGroupType",
		Desc:              "www.aliyun.com",
		ResourceDetailFunc: func(_ context.Context, service schema.ServiceInterface, res chan<- any) error {
			var ptr *int
			// will panic
			fmt.Println(*ptr)
			res <- *ptr
			return nil
		},
		RowField: schema.RowField{
			ResourceId: "$.ResourceId",
		},
		Dimension: schema.Regional,
	}
}
