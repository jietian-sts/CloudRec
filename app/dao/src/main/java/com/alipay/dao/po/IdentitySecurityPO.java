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

public class IdentitySecurityPO {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String resourceId;

    private String tags;

    private String accessType;

    private String resourceType;

    private String accessInfos;

    private String userInfo;

    private String policies;

    private String activityLogs;

    private String instance;

    private String platform;

    private String cloudAccountId;

    private String ruleIds;

    public String getResourceTypeGroup() {
        return resourceTypeGroup;
    }

    public void setResourceTypeGroup(String resourceTypeGroup) {
        this.resourceTypeGroup = resourceTypeGroup;
    }

    private String resourceTypeGroup;

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getAccessInfos() {
        return accessInfos;
    }

    public void setAccessInfos(String accessInfos) {
        this.accessInfos = accessInfos;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }

    public String getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(String activityLogs) {
        this.activityLogs = activityLogs;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCloudAccountId() {
        return cloudAccountId;
    }

    public void setCloudAccountId(String cloudAccountId) {
        this.cloudAccountId = cloudAccountId;
    }

    public String getRuleIds() {
        return ruleIds;
    }

    public void setRuleIds(String ruleId) {
        this.ruleIds = ruleId;
    }
}
