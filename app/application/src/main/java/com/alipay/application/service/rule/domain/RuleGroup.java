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
package com.alipay.application.service.rule.domain;


import com.alipay.common.constant.RuleGroupConstants;
import com.alipay.dao.po.RuleGroupPO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/*
 *@title Rule
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/2/13 14:53
 */
@Getter
@Setter
public class RuleGroup {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String groupName;

    private String groupDesc;

    private String username;

    private String userId;

    private Date lastScanStartTime;

    private Date lastScanEndTime;

    private List<Long> aboutRuleIdList;

    public static RuleGroup setDefaultGroup() {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setGroupDesc(RuleGroupConstants.DEFAULT_GROUP);
        ruleGroup.setGroupName(RuleGroupConstants.DEFAULT_GROUP);
        ruleGroup.setUsername(RuleGroupConstants.SYSTEM);
        return ruleGroup;
    }

    public static RuleGroup toEntity(RuleGroupPO ruleGroupPO) {
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setId(ruleGroupPO.getId());
        ruleGroup.setGmtCreate(ruleGroupPO.getGmtCreate());
        ruleGroup.setGmtModified(ruleGroupPO.getGmtModified());
        ruleGroup.setGroupName(ruleGroupPO.getGroupName());
        ruleGroup.setGroupDesc(ruleGroupPO.getGroupDesc());
        ruleGroup.setUsername(ruleGroupPO.getUsername());
        ruleGroup.setUserId(ruleGroupPO.getUsername());
        ruleGroup.setLastScanStartTime(ruleGroupPO.getLastScanStartTime());
        ruleGroup.setLastScanEndTime(ruleGroupPO.getLastScanEndTime());
        return ruleGroup;
    }
}
