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

package rds

import (
	"context"
	"fmt"

	"github.com/cloudrec/hws/collector"

	"github.com/core-sdk/constant"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	rds "github.com/huaweicloud/huaweicloud-sdk-go-v3/services/rds/v3"
	"github.com/huaweicloud/huaweicloud-sdk-go-v3/services/rds/v3/model"
	"go.uber.org/zap"
)

func GetRDSInstanceResource() schema.Resource {
	return schema.Resource{
		ResourceTypeName:   collector.RDS,
		ResourceType:       "RDS Instance",
		ResourceGroupType:  constant.DATABASE,
		Desc:               "https://console.huaweicloud.com/apiexplorer/#/openapi/RDS/sdk?api=ListInstances",
		ResourceDetailFunc: GetInstanceDetail,
		RowField: schema.RowField{
			ResourceId:   "$.Instance.id",
			ResourceName: "$.Instance.name",
		},
		Dimension: schema.Regional,
	}
}

type InstanceDetail struct {
	Instance       model.InstanceResponse
	AuditlogPolicy *model.ShowAuditlogPolicyResponse
}

func GetInstanceDetail(ctx context.Context, service schema.ServiceInterface, res chan<- any) error {
	cli := service.(*collector.Services).RDS

	request := &model.ListInstancesRequest{}
	offsetRequest := int32(0)
	request.Offset = &offsetRequest
	limitRequest := int32(100)
	request.Limit = &limitRequest

	for {
		instances, err := cli.ListInstances(request)
		if err != nil {
			log.CtxLogger(ctx).Warn("ListInstances error", zap.Error(err))
			return err
		}

		for _, instance := range *instances.Instances {
			res <- &InstanceDetail{
				Instance:       instance,
				AuditlogPolicy: listAuditlogPolicy(ctx, cli, instance.Id),
			}
		}

		if len(*instances.Instances) < 100 {
			break
		}

		offsetRequest = offsetRequest + 1
		request.Offset = &offsetRequest

	}

	return nil
}

func listAuditlogPolicy(ctx context.Context, cli *rds.RdsClient, instanceId string) *model.ShowAuditlogPolicyResponse {
	request := &model.ShowAuditlogPolicyRequest{}
	request.InstanceId = instanceId
	response, err := cli.ShowAuditlogPolicy(request)
	fmt.Print(response)
	if err != nil {
		log.CtxLogger(ctx).Warn("ShowAuditlogPolicy error", zap.Error(err))
		return nil
	}
	return response
}
