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
package com.alipay.application.service.rule.domain.repo.factory;


import com.alipay.application.service.rule.domain.GlobalVariable;
import com.alipay.application.service.rule.domain.RuleAgg;
import com.alipay.application.service.system.domain.enums.Status;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
 *@title RuleFactoryImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/18 16:16
 */
@Component
public class RuleFactoryImpl implements RuleFactory {

    @Override
    public RuleAgg convertToRule(MetadataParser.Metadata metadata, String regoPolicy, List<String> globalVariablePathList) {
        RuleAgg ruleAgg = new RuleAgg();

        ruleAgg.setPlatform(metadata.getPlatform());
        ruleAgg.setResourceType(metadata.getResourceType());
        ruleAgg.setRuleName(metadata.getName());
        ruleAgg.setRuleCode(metadata.getCode());
        ruleAgg.setRuleDesc(metadata.getDescription());
        ruleAgg.setAdvice(metadata.getAdvice());
        ruleAgg.setLinkedDataList(metadata.getLinkedDataList());
        ruleAgg.setRuleTypeList(metadata.getCategoryList());
        ruleAgg.setLink(metadata.getLink());
        ruleAgg.setAdvice(metadata.getAdvice());
        ruleAgg.setRiskLevel(metadata.getLevel());
        ruleAgg.setStatus(Status.valid.name());
        ruleAgg.setRiskCount(0);
        ruleAgg.setContext(metadata.getContext());
        ruleAgg.setUserId("SYSTEM");
        ruleAgg.setRegoPolicy(regoPolicy);

        if (CollectionUtils.isNotEmpty(globalVariablePathList)) {
            List<GlobalVariable> globalVariables = new ArrayList<>();
            for (String globalVariablePath : globalVariablePathList) {
                GlobalVariable globalVariable = new GlobalVariable();
                globalVariable.setPath(globalVariablePath);
                globalVariables.add(globalVariable);
            }
            ruleAgg.setGlobalVariables(globalVariables);
        }

        return ruleAgg;
    }

    @Override
    public MetadataParser.Metadata convertToMetadata(RuleAgg ruleAgg) {
        MetadataParser.Metadata metadata = new MetadataParser.Metadata();
        metadata.setPlatform(ruleAgg.getPlatform());
        metadata.setResourceType(ruleAgg.getResourceType());
        metadata.setName(ruleAgg.getRuleName());
        metadata.setCode(ruleAgg.getRuleCode());
        metadata.setDescription(ruleAgg.getRuleDesc());
        metadata.setAdvice(ruleAgg.getAdvice());
        metadata.setCategoryList(ruleAgg.getRuleTypeList());
        metadata.setLink(ruleAgg.getLink());
        metadata.setAdvice(ruleAgg.getAdvice());
        metadata.setLevel(ruleAgg.getRiskLevel());
        metadata.setContext(ruleAgg.getContext());
        metadata.setLinkedDataList(ruleAgg.getLinkedDataList());

        return metadata;
    }
}
