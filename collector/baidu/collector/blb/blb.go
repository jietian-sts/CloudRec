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

package blb

import (
	"context"
	"github.com/baidubce/bce-sdk-go/services/blb"
	"github.com/cloudrec/baidu/collector"
	"github.com/core-sdk/constant"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	"go.uber.org/zap"
)

type Detail struct {
	Blb blb.BLBModel
}

func GetResource() schema.Resource {
	return schema.Resource{
		ResourceType:      collector.BLB,
		ResourceTypeName:  "BLB",
		ResourceGroupType: constant.NET,
		Desc:              `https://cloud.baidu.com/doc/BLB/index.html`,
		Regions: []string{
			"blb.bj.baidubce.com",
			"blb.gz.baidubce.com",
			"blb.su.baidubce.com",
			"blb.hkg.baidubce.com",
			"blb.fwh.baidubce.com",
			"blb.bd.baidubce.com",
			"blb.fsh.baidubce.com",
			"blb.sin.baidubce.com",
		},
		ResourceDetailFunc: func(ctx context.Context, service schema.ServiceInterface, res chan<- any) error {
			client := service.(*collector.Services).BLBClient

			args := &blb.DescribeLoadBalancersArgs{}
			for {
				response, err := client.DescribeLoadBalancers(args)
				if err != nil {
					log.CtxLogger(ctx).Warn("DescribeLoadBalancers error", zap.Error(err))
					return err
				}
				for _, i := range response.BlbList {
					d := Detail{
						Blb: i,
					}
					res <- d
				}
				if response.NextMarker == "" {
					break
				}
				args.Marker = response.NextMarker
			}

			return nil
		},
		RowField: schema.RowField{
			ResourceId:   "$.Blb.blbId",
			ResourceName: "$.Blb.name",
			Address:      "$.Blb.PublicIp",
		},
		Dimension: schema.Global,
	}
}
