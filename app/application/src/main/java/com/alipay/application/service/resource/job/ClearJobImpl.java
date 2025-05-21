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
package com.alipay.application.service.resource.job;


import com.alipay.application.service.common.utils.DbCacheUtil;
import com.alipay.common.enums.ResourceStatus;
import com.alipay.common.enums.Status;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.mapper.CloudResourceInstanceMapper;
import com.alipay.dao.mapper.ResourceMapper;
import com.alipay.dao.mapper.RuleScanResultMapper;
import com.alipay.dao.po.CloudAccountPO;
import com.alipay.dao.po.ResourcePO;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/*
 *@title ClearJobImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/12/31 10:35
 */

@Slf4j
@Component
public class ClearJobImpl implements ClearJob {

    @Resource
    private ResourceMapper resourceMapper;

    @Resource
    private CloudResourceInstanceMapper cloudResourceInstanceMapper;

    @Resource
    private RuleScanResultMapper ruleScanResultMapper;

    @Resource
    private CloudAccountMapper cloudAccountMapper;

    @Resource
    private DbCacheUtil dbCacheUtil;

    /**
     * Delete data if more than one versions are not updated
     */
    public static final int MAX_STORE_VERSION = 1;

    /**
     * The asset is deleted if it has not been updated for more than 7 days
     */
    private static final int MAX_STORE_DAY = 7;

    @Override
    public void clearObsoleteData() {
        log.info("clear obsolete data start");
        List<ResourcePO> resourceList = resourceMapper.findAll();
        for (ResourcePO resourcePO : resourceList) {
            List<String> cloudAccountIdList = cloudResourceInstanceMapper.findAccountList(resourcePO.getPlatform(), resourcePO.getResourceType());
            for (String cloudAccountId : cloudAccountIdList) {
                clearExpiredDataByCloudAccount(cloudAccountId, resourcePO.getPlatform(), resourcePO.getResourceType());

                while (true) {
                    int effectCount = cloudResourceInstanceMapper.deleteDiscardedData(cloudAccountId, resourcePO.getResourceType());
                    if (effectCount == 0) {
                        break;
                    }
                }
            }
        }
        log.info("clear obsolete data end");
    }


    public void clearExpiredDataByCloudAccount(String cloudAccountId, String platform, String resourceType) {
        CloudAccountPO cloudAccountPO = cloudAccountMapper.findByCloudAccountId(cloudAccountId);
        if (cloudAccountPO != null && Objects.equals(Status.running.name(), cloudAccountPO.getCollectorStatus())) {
            log.warn("cloud account {} is running, skip clear resourceType {} obsolete data", cloudAccountId, resourceType);
            return;
        }
        try {
            Thread.sleep(1000);
            log.info("clear obsolete data start, cloudAccountId:{},resourceType:{}", cloudAccountId, resourceType);
            List<String> versionList = cloudResourceInstanceMapper.findVersionList(platform, cloudAccountId, resourceType);
            if (versionList.isEmpty()) {
                log.info("No version data found for resource type: {} account :{}", resourceType, cloudAccountId);
                return;
            }
            versionList = versionList.stream().filter(Objects::nonNull).toList();
            if (versionList.size() > MAX_STORE_VERSION) {
                List<String> expiredversionList = versionList.stream().sorted().limit(versionList.size() - MAX_STORE_VERSION).toList();
                List<Long> idList = cloudResourceInstanceMapper.findExpiredVersionDataList(platform, cloudAccountId, resourceType, expiredversionList);
                if (!idList.isEmpty()) {
                    log.info("Expired version data found for resource type: {} account :{} expired version list: {} effectCount:{}", resourceType, cloudAccountId, expiredversionList, idList.size());
                    // idList too large, split and delete
                    List<List<Long>> idListSplit = Lists.partition(idList, 300);
                    for (List<Long> idListSub : idListSplit) {
                        Thread.sleep(1000);
                        cloudResourceInstanceMapper.deletedByIdList(idListSub);
                        ruleScanResultMapper.updateResourceStatus(idListSub, ResourceStatus.not_exist.name());
                    }
                }
            }

            while (true) {
                int effectCount = cloudResourceInstanceMapper.deleteByModified(cloudAccountId, resourceType, MAX_STORE_DAY);
                log.info("ResourceType:{}, Deleted {} expired data for account: {}", resourceType, effectCount, cloudAccountId);
                if (effectCount == 0) {
                    break;
                }
            }

        } catch (Exception e) {
            log.error("clear obsolete data error", e);
        }
    }

    @Override
    public void cacheClearHandler() {
        dbCacheUtil.clear();
    }

}


