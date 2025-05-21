/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.application.service.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.application.share.request.rule.AccountExampleInfo;
import com.alipay.application.share.request.rule.WhitedScanInputDataDTO;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.mapper.CloudResourceInstanceMapper;
import com.alipay.dao.po.CloudAccountPO;
import com.alipay.dao.po.CloudResourceInstancePO;
import com.alipay.dao.po.RuleScanResultPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Date: 2025/3/26
 * Author: lz
 */

@Component
public class WhitedExampleDataComponent {

    @Resource
    private CloudAccountMapper cloudAccountMapper;
    @Resource
    private CloudResourceInstanceMapper cloudResourceInstanceMapper;

    public WhitedScanInputDataDTO buildWhitedExampleDataResultDTO(RuleScanResultPO ruleScanResultPO, CloudAccountPO cloudAccountPO, CloudResourceInstancePO resourceInstance) {
        WhitedScanInputDataDTO whitedExampleDataResultDTO = new WhitedScanInputDataDTO();
        whitedExampleDataResultDTO.setRiskInfo(JSONObject.parseObject(ruleScanResultPO.getResult()));
        if (Objects.isNull(cloudAccountPO)) {
            cloudAccountPO = cloudAccountMapper.findByCloudAccountId(ruleScanResultPO.getCloudAccountId());
        }
        if (Objects.nonNull(cloudAccountPO)) {
            AccountExampleInfo accountInfo = new AccountExampleInfo();
            accountInfo.setUserId(cloudAccountPO.getUserId());
            accountInfo.setCloudAccountId(cloudAccountPO.getCloudAccountId());
            accountInfo.setOwner(cloudAccountPO.getOwner());
            accountInfo.setPlatform(cloudAccountPO.getPlatform());
            whitedExampleDataResultDTO.setAccountInfo(accountInfo);
            if (Objects.isNull(resourceInstance)) {
                resourceInstance = cloudResourceInstanceMapper.findOne(cloudAccountPO.getPlatform(), ruleScanResultPO.getResourceType(),
                        ruleScanResultPO.getCloudAccountId(), ruleScanResultPO.getResourceId());
            }
            whitedExampleDataResultDTO.setResourceInstance(resourceInstance == null ? null : JSON.parseObject(resourceInstance.getInstance()));
        }
        return whitedExampleDataResultDTO;
    }
}
