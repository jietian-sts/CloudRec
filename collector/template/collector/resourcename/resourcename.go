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

package resourcename

import (
	"context"
	"github.com/core-sdk/schema"
	"template/collector"
)

// [5.1] ADD_NEW_CLOUD :
// 1. Change the function name GetSomeResource
// 2. Change ResourceType, ResourceTypeName, ResourceGroupType
func GetSomeResource() schema.Resource {
	return schema.Resource{
		ResourceType:      collector.ResourceName,
		ResourceTypeName:  "ResourceNameDisplayed", // resource name displayed on website
		ResourceGroupType: "",                      // choose enum type from core-sdk/constant/resource_group_type.go
		Desc:              "",
		ResourceDetailFunc: func(_ context.Context, service schema.ServiceInterface, res chan<- any) error {
			// [5.2] ADD_NEW_CLOUD : Implement the collect function and push results to channel
			// example:

			//client := service.(*collector.Services).OSS
			//buckets, err := client.ListBuckets(ctx, ListBucketsInput{})
			//if err != nil {
			//	log.CtxLogger(ctx).Warn("ListBuckets error", zap.Error(err))
			//	return err
			//}
			//
			//for _, bucket := range buckets {
			//	res <- BucketDetail{
			//		Bucket: bucket,
			//	}
			//}

			return nil
		},
		RowField: schema.RowField{
			ResourceId: "$.Bucket.name",
		},
		Dimension: schema.Regional,
	}
}

//type BucketDetail struct {
//	Bucket types.Bucket
//}
