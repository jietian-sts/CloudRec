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
	"fmt"
	"github.com/baidubce/bce-sdk-go/services/bcc"
	"github.com/baidubce/bce-sdk-go/services/blb"
	"github.com/baidubce/bce-sdk-go/services/bos"
	"github.com/baidubce/bce-sdk-go/services/eip"
	"github.com/baidubce/bce-sdk-go/services/rds"
	"github.com/baidubce/bce-sdk-go/services/vpc"
	"github.com/core-sdk/log"
	"github.com/core-sdk/schema"
)

type Services struct {
	VPCClient *vpc.Client
	BCCClient *bcc.Client
	BLBClient *blb.Client
	BOSClient *bos.Client
	RDSClient *rds.Client
	EIPClient *eip.Client
}

func (s *Services) InitServices(cloudAccountParam schema.CloudAccountParam) (err error) {
	param := cloudAccountParam.CommonCloudAccountParam
	switch cloudAccountParam.ResourceType {
	case SECURITY_GROUP, BCC:
		SgClient, err := bcc.NewClient(param.AK, param.SK, param.Region)
		if err != nil {
			log.GetWLogger().Warn(fmt.Sprintf("init sg client failed, err: %s", err))
		}
		s.BCCClient = SgClient
	case VPC:
		vpcClient, err := vpc.NewClient(param.AK, param.SK, param.Region)
		if err != nil {
			log.GetWLogger().Warn(fmt.Sprintf("init vpc client failed, err: %s", err))
		}
		s.VPCClient = vpcClient
	case BLB:
		blbClient, err := blb.NewClient(param.AK, param.SK, param.Region)
		if err != nil {
			log.GetWLogger().Warn(fmt.Sprintf("init blb client failed, err: %s", err))
		}
		s.BLBClient = blbClient
	case BOS:
		bosClient, err := bos.NewClient(param.AK, param.SK, param.Region)
		if err != nil {
			log.GetWLogger().Warn(fmt.Sprintf("init bos client failed, err: %s", err))
		}
		s.BOSClient = bosClient
	case RDS:
		rdsClient, err := rds.NewClient(param.AK, param.SK, param.Region)
		if err != nil {
			log.GetWLogger().Warn(fmt.Sprintf("init rds client failed, err: %s", err))
		}
		s.RDSClient = rdsClient

	case EIP:
		eipClient, err := eip.NewClient(param.AK, param.SK, param.Region)
		if err != nil {
			log.GetWLogger().Warn(fmt.Sprintf("init eip client failed, err: %s", err))
		}
		s.EIPClient = eipClient
	}

	return nil
}
