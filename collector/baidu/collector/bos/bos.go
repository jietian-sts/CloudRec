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

package bos

import (
	"context"
	"github.com/baidubce/bce-sdk-go/services/bos"
	"github.com/baidubce/bce-sdk-go/services/bos/api"
	"github.com/cloudrec/baidu/collector"
	"github.com/core-sdk/constant"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
	"go.uber.org/zap"
)

type Detail struct {
	Bucket api.BucketSummaryType

	GetBucketAclResult *api.GetBucketAclResult
}

func GetResource() schema.Resource {
	return schema.Resource{
		ResourceType:      collector.BOS,
		ResourceTypeName:  "BOS",
		ResourceGroupType: constant.STORE,
		Desc:              `https://cloud.baidu.com/product/bos.html`,
		Regions: []string{
			"bj.bcebos.com",
			"gz.bcebos.com",
			"su.bcebos.com",
		},
		ResourceDetailFunc: func(ctx context.Context, service schema.ServiceInterface, res chan<- any) error {
			client := service.(*collector.Services).BOSClient

			if resp, err := client.ListBuckets(); err != nil {
				log.CtxLogger(ctx).Warn("ListBuckets error", zap.Error(err))
			} else {
				for _, b := range resp.Buckets {
					detail := Detail{
						Bucket:             b,
						GetBucketAclResult: getBucketAcl(ctx, client, b.Name),
					}
					res <- detail
				}
			}

			return nil
		},
		RowField: schema.RowField{
			ResourceId:   "$.Bucket.name",
			ResourceName: "$.Bucket.name",
		},
		Dimension: schema.Regional,
	}
}

func getBucketAcl(ctx context.Context, BOSClient *bos.Client, bucket string) *api.GetBucketAclResult {
	acl, err := BOSClient.GetBucketAcl(bucket)
	if err != nil {
		log.CtxLogger(ctx).Warn("GetBucketAcl error", zap.Error(err))
		return nil
	}
	return acl

}
