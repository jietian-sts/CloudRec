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
package com.alipay.application.service.rule.domain.repo;


import com.alipay.application.service.rule.domain.RuleGroup;
import com.alipay.common.constant.RuleGroupConstants;
import com.alipay.dao.context.UserInfoContext;
import com.alipay.dao.mapper.RuleGroupMapper;
import com.alipay.dao.mapper.RuleGroupRelMapper;
import com.alipay.dao.mapper.RuleMapper;
import com.alipay.dao.mapper.UserMapper;
import com.alipay.dao.po.RuleGroupPO;
import com.alipay.dao.po.RuleGroupRelPO;
import com.alipay.dao.po.RulePO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/*
 *@title RuleGroupRepositoryImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/2/13 17:11
 */
@Repository
public class RuleGroupRepositoryImpl implements RuleGroupRepository {

    @Resource
    private RuleGroupMapper ruleGroupMapper;

    @Resource
    private RuleMapper ruleMapper;

    @Resource
    private RuleGroupRelMapper ruleGroupRelMapper;

    @Resource
    private RuleGroupConverter ruleGroupConverter;

    @Resource
    private UserMapper userMapper;

    @Override
    public RuleGroup findOne(Long id) {
        RuleGroupPO ruleGroupPO = ruleGroupMapper.selectByPrimaryKey(id);
        if (ruleGroupPO == null) {
            return null;
        }
        return ruleGroupConverter.toEntity(ruleGroupPO);
    }

    @Override
    public RuleGroup findByName(String name) {
        RuleGroupPO ruleGroupPO = ruleGroupMapper.findOne(name);
        if (ruleGroupPO == null) {
            return null;
        }
        return ruleGroupConverter.toEntity(ruleGroupPO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public long save(RuleGroup ruleGroup) {
        if (ruleGroup.getId() == null) {
            RuleGroupPO ruleGroupPO = ruleGroupConverter.toPo(ruleGroup);
            ruleGroupPO.setUsername(UserInfoContext.getCurrentUser() == null ? RuleGroupConstants.SYSTEM : UserInfoContext.getCurrentUser().getUsername());
            ruleGroupMapper.insertSelective(ruleGroupPO);
            ruleGroup = ruleGroupConverter.toEntity(ruleGroupPO);
        } else {
            ruleGroupMapper.updateByPrimaryKeySelective(ruleGroupConverter.toPo(ruleGroup));
        }

        return ruleGroup.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void join(Long groupId, List<Long> ruleIdList) {
        ruleGroupRelMapper.deleteByRuleGroupId(groupId);
        if (CollectionUtils.isNotEmpty(ruleIdList)) {
            for (Long ruleId : ruleIdList) {
                join(groupId, ruleId);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void join(Long groupId, Long ruleId) {
        RuleGroupRelPO ruleGroupRelPO = ruleGroupRelMapper.queryOne(ruleId, groupId);
        if (ruleGroupRelPO == null) {
            RulePO rulePO = ruleMapper.selectByPrimaryKey(ruleId);
            ruleGroupRelPO = new RuleGroupRelPO();
            ruleGroupRelPO.setRuleId(ruleId);
            ruleGroupRelPO.setRuleGroupId(groupId);
            ruleGroupRelPO.setRuleCode(rulePO.getRuleCode());
            ruleGroupRelMapper.insertSelective(ruleGroupRelPO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void join(List<Long> groupIdList, Long ruleId) {
        ruleGroupRelMapper.deleteByRuleId(ruleId);
        if (CollectionUtils.isNotEmpty(groupIdList)) {
            for (Long groupId : groupIdList) {
                join(groupId, ruleId);
            }
        }
    }

    @Override
    public List<RuleGroup> findByRuleId(Long ruleId) {
        List<RuleGroupRelPO> list = ruleGroupRelMapper.queryByRuleId(ruleId);
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }

        List<RuleGroup> result = new ArrayList<>();
        for (RuleGroupRelPO ruleGroupRelPO : list) {
            RuleGroupPO ruleGroupPO = ruleGroupMapper.selectByPrimaryKey(ruleGroupRelPO.getRuleGroupId());
            RuleGroup ruleGroup = ruleGroupConverter.toEntity(ruleGroupPO);
            result.add(ruleGroup);
        }
        return result;
    }
}
