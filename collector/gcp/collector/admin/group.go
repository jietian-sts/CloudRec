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

package admin

import (
	"context"
	"github.com/cloudrec/gcp/collector"
	"github.com/core-sdk/constant"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	"go.uber.org/zap"
	admin "google.golang.org/api/admin/directory/v1"
)

func GetGroupResource() schema.Resource {
	return schema.Resource{
		ResourceType:      collector.GoogleGroup,
		ResourceTypeName:  collector.GoogleGroup,
		ResourceGroupType: constant.IDENTITY,
		Desc:              ``,
		ResourceDetailFunc: func(ctx context.Context, service schema.ServiceInterface, res chan<- any) error {
			svc := service.(*collector.Services).Admin

			if err := svc.Groups.List().Pages(ctx, func(resp *admin.Groups) error {
				for _, group := range resp.Groups {
					res <- GroupDetail{
						Group: group,
					}
				}
				return nil
			},
			); err != nil {
				log.CtxLogger(ctx).Warn("ListGroups error", zap.Error(err))
				return err
			}

			return nil
		},
		RowField: schema.RowField{
			ResourceId:   "$.Group.id",
			ResourceName: "$.Group.name",
		},
		Dimension: schema.Global,
	}
}

type GroupDetail struct {
	Group *admin.Group
}
