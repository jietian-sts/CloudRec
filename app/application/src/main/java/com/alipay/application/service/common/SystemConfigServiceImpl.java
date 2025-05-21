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

import com.alipay.application.share.request.common.SystemConfigRequest;
import com.alipay.common.enums.SystemConfigEnum;
import com.alipay.dao.mapper.SystemConfigMapper;
import com.alipay.dao.po.SystemConfigPO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Date: 2025/4/28
 * Author: lz
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    public static final String COMMA_SEPARATOR = ",";

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Override
    public SystemConfigPO save(SystemConfigRequest request) {
        SystemConfigPO systemConfigPO = new SystemConfigPO();
        if (request.getId() == null) {
            BeanUtils.copyProperties(request, systemConfigPO);
            systemConfigPO.setGmtCreate(new Date());
            systemConfigPO.setGmtModified(new Date());
            systemConfigMapper.insertSelective(systemConfigPO);
        } else {
            systemConfigPO = systemConfigMapper.selectByPrimaryKey(request.getId());
            if (systemConfigPO == null) {
                return null;
            }
            systemConfigPO.setConfigType(request.getConfigType());
            systemConfigPO.setConfigKey(request.getConfigKey());
            systemConfigPO.setConfigValue(request.getConfigValue());
            systemConfigMapper.updateByPrimaryKeySelective(systemConfigPO);
        }
        return systemConfigPO;
    }

    @Override
    public int deleteById(Long id) {
        if (id != null) {
            return systemConfigMapper.deleteByPrimaryKey(id);
        }
        return 0;
    }

    @Override
    public String getAliNoAclRuleIds() {
        SystemConfigPO systemConfigPO = systemConfigMapper.findOne(SystemConfigEnum.ALI_NO_ACL_RULEID.getConfigType(),
                SystemConfigEnum.ALI_NO_ACL_RULEID.getConfigKey());
        return systemConfigPO.getConfigValue();
    }

    @Override
    public String getAliInactiveRuleIds() {
        SystemConfigPO systemConfigPO = systemConfigMapper.findOne(SystemConfigEnum.ALI_INACTIVE_RULEID.getConfigType(),
                SystemConfigEnum.ALI_INACTIVE_RULEID.getConfigKey());
        return systemConfigPO.getConfigValue();
    }
}
