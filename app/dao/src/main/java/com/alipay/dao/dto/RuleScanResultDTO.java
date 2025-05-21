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
package com.alipay.dao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RuleScanResultDTO extends PageDTO {

    private Long id;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 规则id列表
     */
    private List<Long> ruleIdList;

    /**
     * 版本
     */
    private Long version;

    /**
     * 云账号id
     */
    private String cloudAccountId;

    /**
     * 云账号id列表
     */
    private List<String> cloudAccountIdList;

    /**
     * tenantId
     */
    private Long tenantId;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源ID或名称
     */
    private String resourceIdOrName;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 平台
     */
    private String platform;

    /**
     * 平台列表
     */
    private List<String> platformList;

    /**
     * 资产类型
     */
    private String resourceType;

    /**
     * 扫描结果的详细信息
     */
    private String result;

    /**
     * 区域信息
     */
    private String region;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态列表
     */
    private List<String> statusList;

    /**
     * 是否是新风险
     */
    private Integer isNew;

    /**
     * 忽略的原因类型
     */
    private String ignoreReasonType;

    /**
     * 忽略的原因类型
     */
    private List<String> ignoreReasonTypeList;

    /**
     * 忽略的原因
     */
    private String ignoreReason;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则组id列表
     */
    private List<Long> ruleGroupIdList;

    /**
     * 风险等级
     */
    private List<String> riskLevelList;

    /**
     * 资产类型
     */
    private List<String> resourceTypeList;

    /**
     * 规则类型id列表
     */
    private List<Long> ruleTypeIdList;


    private List<String> ruleCodeList;

    private String gmtCreateStart;

    private String gmtCreateEnd;

    private String gmtModifiedStart;

    private String gmtModifiedEnd;

    /**
     * 资源状态
     */
    private String resourceStatus;
}