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

package actiontrail

import (
	"context"
	"github.com/aliyun/alibaba-cloud-sdk-go/services/actiontrail"
	"github.com/cloudrec/alicloud/collector"
	"github.com/core-sdk/constant"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	"go.uber.org/zap"
)

func GetActionTrailResource() schema.Resource {
	return schema.Resource{
		ResourceType:       collector.ActionTrail,
		ResourceTypeName:   collector.ActionTrail,
		ResourceGroupType:  constant.CONFIG,
		Desc:               `https://api.aliyun.com/product/Actiontrail`,
		ResourceDetailFunc: GetInstanceDetail,
		RowField:           schema.RowField{},
		Dimension:          schema.Global,
	}
}

type Detail struct {
	OpenActionTrail bool
	TrailList       []actiontrail.Trail
}

func GetInstanceDetail(ctx context.Context, service schema.ServiceInterface, res chan<- any) error {
	cli := service.(*collector.Services).Actiontrail

	request := actiontrail.CreateDescribeTrailsRequest()
	request.Scheme = "https"
	request.IncludeOrganizationTrail = "true"

	// No paging required
	response, err := cli.DescribeTrails(request)
	if err != nil {
		log.CtxLogger(ctx).Warn("DescribeTrails error", zap.Error(err))
		return err
	}
	if response.TrailList == nil {
		res <- Detail{
			OpenActionTrail: false,
			TrailList:       []actiontrail.Trail{},
		}
	} else {
		// Getting trails means ActionTrail Service is enabled
		res <- Detail{
			OpenActionTrail: true,
			TrailList:       response.TrailList,
		}
	}

	return nil
}
