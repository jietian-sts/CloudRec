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

package collector

import (
	"github.com/core-sdk/schema"
)

// Services 需要每一个云平台实现
type Services struct {
	// [3] ADD_NEW_CLOUD :
	// example:
	//OSS *oss.Client
}

func (s *Services) InitServices(cloudAccountParam schema.CloudAccountParam) (err error) {
	// [4] ADD_NEW_CLOUD : init clients need
	// example:

	//param := cloudAccountParam.CommonCloudAccountParam
	//switch cloudAccountParam.ResourceType {
	//case Bucket:
	//case ResourceName:
	//	s.OSS, err = oss.NewClientWithAccessKey(param.Region, param.AK, param.SK)
	//	if err != nil {
	//		return fmt.Errorf("failed to initialize oss client: %w", err)
	//	}
	//}

	return nil
}
