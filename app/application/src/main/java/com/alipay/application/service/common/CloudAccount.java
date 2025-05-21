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
package com.alipay.application.service.common;


import com.alipay.dao.dto.CloudAccountDTO;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.po.CloudAccountPO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 *@title CloudAccount
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/14 15:26
 */
@Component
public class CloudAccount {

    @Resource
    private CloudAccountMapper cloudAccountMapper;

    public List<String> queryCloudAccountIdList(String cloudAccountSearch) {
        if (StringUtils.isBlank(cloudAccountSearch)) {
            return Collections.emptyList();
        }
        CloudAccountDTO cloudAccountDTO = CloudAccountDTO.builder().cloudAccountSearch(cloudAccountSearch).build();
        List<CloudAccountPO> cloudAccountPOS = cloudAccountMapper.findList(cloudAccountDTO);
        return cloudAccountPOS.stream().map(CloudAccountPO::getCloudAccountId).collect(Collectors.toList());
    }
}
