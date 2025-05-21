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
package com.alipay.dao.po;

import java.util.Date;

public class WhitedRuleConfigPO {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String ruleType;

    private String ruleName;

    private String ruleDesc;

    private String ruleConfig;

    private String condition;

    private String ruleConfigJson;

    private String regoContent;

    private Long tenantId;

    private String creator;

    private String lockHolder;

    private int enable;

    private String riskRuleCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getRuleConfig() {
        return ruleConfig;
    }

    public void setRuleConfig(String ruleConfig) {
        this.ruleConfig = ruleConfig;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getRuleConfigJson() {
        return ruleConfigJson;
    }

    public void setRuleConfigJson(String ruleConfigJson) {
        this.ruleConfigJson = ruleConfigJson;
    }

    public String getRegoContent() {
        return regoContent;
    }

    public void setRegoContent(String regoContent) {
        this.regoContent = regoContent;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(String lockHolder) {
        this.lockHolder = lockHolder;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getRiskRuleCode() {
        return riskRuleCode;
    }

    public void setRiskRuleCode(String riskRuleCode) {
        this.riskRuleCode = riskRuleCode;
    }
}