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
package com.alipay.application.service.rule.exposed;


import com.alipay.application.service.rule.domain.RuleGroup;
import com.alipay.application.service.rule.domain.repo.RuleGroupRepository;
import com.alipay.common.constant.RuleGroupConstants;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/*
 *@title GroupJoinServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/2/13 16:56
 */
@Component
public class GroupJoinServiceImpl implements GroupJoinService {

    @Resource
    private RuleGroupRepository ruleGroupRepository;


    @Override
    public void joinDefaultGroup(Long ruleId) {
        ruleGroupRepository.join(initDefaultGroup(), ruleId);
    }

    @Override
    public long initDefaultGroup() {
        RuleGroup defaultGroup = ruleGroupRepository.findByName(RuleGroupConstants.DEFAULT_GROUP);
        if (defaultGroup != null) {
            return defaultGroup.getId();
        }

        RuleGroup ruleGroup = RuleGroup.setDefaultGroup();
        return ruleGroupRepository.save(ruleGroup);
    }
}
