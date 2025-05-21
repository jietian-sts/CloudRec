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
package com.alipay.application.service.resource.exposed;


import com.alipay.dao.mapper.CloudResourceInstanceMapper;
import com.alipay.dao.po.CloudResourceInstancePO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

/*
 *@title QueryResourceServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/19 15:52
 */
@Slf4j
@Service
public class QueryResourceServiceImpl implements QueryResourceService {

    @Resource
    private CloudResourceInstanceMapper cloudResourceInstanceMapper;

    /**
     * Query the example data of an asset based on the cloud platform and asset type
     *
     * @param platform     cloud platforms, such as ALI_CLOUD, TENCENT_CLOUD, BAIDU_CLOUD, etc.
     * @param resourceType Asset type, such as ECS, RDS, SLB, etc.
     * @return Asset JSON
     */
    @Override
    public String queryExampleData(String platform, String resourceType) {
        Validate.notBlank(platform, "platform is not blank");
        Validate.notBlank(resourceType, "resourceType is not blank");

        try {
            CloudResourceInstancePO cloudResourceInstancePO = cloudResourceInstanceMapper.findExampleLimit1(platform, resourceType);
            if (cloudResourceInstancePO == null) {
                return "";
            }
            return cloudResourceInstancePO.getInstance();
        } catch (Exception e) {
            log.error("queryExampleData error, platform:{}, resourceType:{}", platform, resourceType, e);
            return "";
        }
    }
}
