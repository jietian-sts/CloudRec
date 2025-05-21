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
package com.alipay.application.service.account.cloud;


import com.alipay.application.service.account.domain.SecurityProductPosture;
import com.alipay.application.service.system.domain.enums.Status;
import com.alipay.dao.dto.CloudAccountDTO;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.mapper.CloudRamMapper;
import com.alipay.dao.mapper.SecurityProductPostureMapper;
import com.alipay.dao.po.CloudAccountPO;
import com.alipay.dao.po.SecurityProductPosturePO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/*
 *@title Producer
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 16:56
 */
@Slf4j
@Component
public class Producer {

    @Resource
    private CloudAccountMapper cloudAccountMapper;
    @Resource
    private SecurityProductPostureMapper securityProductPostureMapper;
    @Resource
    private CloudRamMapper cloudRamMapper;

    public List<CloudAccountPO> getCloudAccountList(String platform) {
        return cloudAccountMapper.findList(CloudAccountDTO.builder().platform(platform).accountStatus(Status.valid.name()).build());
    }


    public boolean deleteRamAclData(String platform) {
        try {
            log.info("deleteRamAclData start");
            while (true) {
                int i = cloudRamMapper.deleteLimit1000(platform);
                if (i == 0) {
                    break;
                }
            }
            log.info("deleteRamAclData end");
            return true;
        } catch (Exception e) {
            log.error("deleteRamAclData error", e);
        }
        return false;
    }

    /**
     * 保存云产品安装信息
     */
    protected void saveSecurityProductPosture(SecurityProductPosture securityProductPosture) {
        CloudAccountPO cloudAccountPO = securityProductPosture.getCloudAccountPO();
        SecurityProductPosturePO securityProductPosturePO = securityProductPostureMapper.findOne(cloudAccountPO.getCloudAccountId(), cloudAccountPO.getPlatform(), securityProductPosture.getProductType());
        if (securityProductPosturePO == null) {
            securityProductPosturePO = new SecurityProductPosturePO();
            securityProductPosturePO.setCloudAccountId(cloudAccountPO.getCloudAccountId());
            securityProductPosturePO.setPlatform(cloudAccountPO.getPlatform());
            securityProductPosturePO.setTenantId(cloudAccountPO.getTenantId());
            securityProductPosturePO.setProductType(securityProductPosture.getProductType());
            securityProductPosturePO.setResourceType(securityProductPosture.getResourceType());
            securityProductPosturePO.setStatus(securityProductPosture.getStatus());
            securityProductPosturePO.setPolicy(securityProductPosture.getPolicy());
            securityProductPosturePO.setPolicyDetail(securityProductPosture.getPolicyDetail());
            securityProductPosturePO.setVersion(securityProductPosture.getVersion());
            securityProductPosturePO.setVersionDesc(securityProductPosture.getVersionDesc());
            securityProductPosturePO.setProtectedCount(securityProductPosture.getProtectedCount() == null ? 0 : securityProductPosture.getProtectedCount());
            securityProductPosturePO.setTotal(securityProductPosture.getTotal() == null ? 0 : securityProductPosture.getTotal());

            securityProductPostureMapper.insertSelective(securityProductPosturePO);
        } else {
            // 可更新的数据：resourceType、status、policy、policyDetail、version、versionDesc、protectedCount、total
            securityProductPosturePO.setResourceType(securityProductPosture.getResourceType());
            securityProductPosturePO.setStatus(securityProductPosture.getStatus());
            securityProductPosturePO.setPolicy(securityProductPosture.getPolicy());
            securityProductPosturePO.setPolicyDetail(securityProductPosture.getPolicyDetail());
            securityProductPosturePO.setVersion(securityProductPosture.getVersion());
            securityProductPosturePO.setVersionDesc(securityProductPosture.getVersionDesc());
            securityProductPosturePO.setProtectedCount(securityProductPosture.getProtectedCount() == null ? 0 : securityProductPosture.getProtectedCount());
            securityProductPosturePO.setTotal(securityProductPosture.getTotal() == null ? 0 : securityProductPosture.getTotal());
            securityProductPosturePO.setTenantId(cloudAccountPO.getTenantId());
            securityProductPosturePO.setGmtModified(new Date());
            securityProductPostureMapper.updateByPrimaryKeySelective(securityProductPosturePO);
        }
    }


}
