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
package com.alipay.application.service.system.domain.repo;


import com.alipay.application.service.system.domain.Tenant;
import com.alipay.dao.dto.TenantDTO;
import com.alipay.dao.mapper.TenantMapper;
import com.alipay.dao.mapper.TenantRuleMapper;
import com.alipay.dao.mapper.TenantUserMapper;
import com.alipay.dao.mapper.UserMapper;
import com.alipay.dao.po.TenantPO;
import com.alipay.dao.po.TenantRulePO;
import com.alipay.dao.po.TenantUserPO;
import com.alipay.dao.po.UserPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/*
 *@title TenantRepositoryImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/17 17:46
 */
@Repository
public class TenantRepositoryImpl implements TenantRepository {

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private TenantUserMapper tenantUserMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TenantConverter tenantConverter;

    @Resource
    private TenantRuleMapper tenantRuleMapper;

    @Override
    public Tenant find(Long id) {
        TenantPO tenantPO = tenantMapper.selectByPrimaryKey(id);
        return tenantConverter.toEntity(tenantPO);
    }


    @Override
    public List<Tenant> findAll(String status) {
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setStatus(status);
        List<TenantPO> list = tenantMapper.findList(tenantDTO);
        return list.stream().map(tenantConverter::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Tenant> findList(String userId) {
        List<TenantPO> tenantPOS = tenantMapper.findListByUserId(userId);
        return tenantPOS.stream().map(tenantConverter::toEntity).collect(Collectors.toList());
    }

    @Override
    public void save(Tenant tenant) {
        TenantPO tenantPO = tenantMapper.selectByPrimaryKey(tenant.getId());
        if (tenantPO == null) {
            tenantMapper.insertSelective(tenantConverter.toPo(tenant));
        } else {
            tenantMapper.updateByPrimaryKeySelective(tenantConverter.toPo(tenant));
        }
    }

    @Override
    public Tenant find(String name) {
        TenantPO tenantPO = tenantMapper.findByTenantName(name);
        return tenantConverter.toEntity(tenantPO);
    }


    @Override
    public int exist(String userId, Long tenantId) {
        UserPO userPO = userMapper.findOne(userId);
        if (userPO == null) {
            return 0;
        }
        return tenantMapper.findRecord(userPO.getId(), tenantId);
    }

    @Override
    public int memberCount(Long id) {
        return tenantMapper.findMemberCountByTenantId(id);
    }

    @Override
    public void join(Long uid, Long tenantId) {
        TenantUserPO tenantUserPO = tenantUserMapper.findOne(uid, tenantId);
        if (tenantUserPO == null) {
            tenantUserPO = new TenantUserPO();
            tenantUserPO.setUserId(uid);
            tenantUserPO.setTenantId(tenantId);
            tenantUserMapper.insertSelective(tenantUserPO);
        }
    }

    @Override
    public void remove(Long uid, Long tenantId) {
        UserPO userPO = userMapper.selectByPrimaryKey(uid);
        if (userPO == null) {
            return;
        }
        tenantUserMapper.del(userPO.getId(), tenantId);
    }

    @Override
    public boolean isSelected(Long tenantId, String ruleCode) {
        TenantRulePO tenantRulePO = tenantRuleMapper.findOne(tenantId, ruleCode);
        return tenantRulePO != null;
    }
}
