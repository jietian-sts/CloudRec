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
import com.alipay.dao.converter.Converter;
import com.alipay.dao.po.RuleGroupPO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/*
 *@title RuleGroupConverter
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/2/13 17:13
 */
@Component
public class RuleGroupConverter implements Converter<RuleGroup, RuleGroupPO> {
    @Override
    public RuleGroupPO toPo(RuleGroup ruleGroup) {
        RuleGroupPO ruleGroupPO = new RuleGroupPO();
        BeanUtils.copyProperties(ruleGroup, ruleGroupPO);
        return ruleGroupPO;
    }

    @Override
    public RuleGroup toEntity(RuleGroupPO ruleGroupPO) {
        RuleGroup ruleGroup = new RuleGroup();
        BeanUtils.copyProperties(ruleGroupPO, ruleGroup);
        return ruleGroup;
    }
}
