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

package ecs

import (
	"context"
	"github.com/aliyun/alibaba-cloud-sdk-go/sdk/requests"
	"github.com/aliyun/alibaba-cloud-sdk-go/services/ecs"
	"github.com/core-sdk/schema"
	"test/collector"
)

type Detail struct {
	Instance ecs.Instance
}

// GetEcsData 数据
func GetEcsData() schema.Resource {
	return schema.Resource{
		ResourceType:     "ECS",
		ResourceTypeName: "ECS",
		Desc:             `https://next.api.aliyun.com/api/Sls/2020-12-30/CreateProject?RegionId=cn-hangzhou&sdkStyle=dara&tab=DEMO&lang=GO`,
		ResourceDetailFunc: func(ctx context.Context, service schema.ServiceInterface, res chan<- any) error {
			client := service.(*collector.Services).ECS
			req := ecs.CreateDescribeInstancesRequest()
			req.PageSize = requests.NewInteger(50)
			req.PageNumber = requests.NewInteger(1)
			req.Scheme = "HTTPS"
			req.QueryParams["product"] = "Ecs"
			req.SetHTTPSInsecure(true)

			count := 0
			for {
				response, err := client.DescribeInstances(req)
				if err != nil {
					return err
				}
				for _, i := range response.Instances.Instance {
					d := Detail{
						Instance: i,
					}

					res <- d
					count++
				}
				if count >= response.TotalCount {
					break
				}
				req.PageNumber = requests.NewInteger(response.PageNumber + 1)
			}

			return nil
		},
		RowField: schema.RowField{
			ResourceId:   "$.Instance.InstanceId",
			ResourceName: "$.Instance.InstanceName",
		},
		Dimension: schema.Regional,
	}
}
