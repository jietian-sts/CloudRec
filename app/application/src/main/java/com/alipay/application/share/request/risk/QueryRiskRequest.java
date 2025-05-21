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
package com.alipay.application.share.request.risk;

import com.alipay.application.share.request.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/*
 *@title QueryRiskListRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/7/16 16:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRiskRequest extends BaseRequest {

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 规则名称
     */
    private List<String> ruleCodeList;

    /**
     * 规则id列表
     */
    private List<Long> ruleIdList;

    /**
     * 规则组id列表
     */
    private List<Long> ruleGroupIdList;

    /**
     * 云账号id
     */
    private String cloudAccountId;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 风险等级
     */
    private List<String> riskLevelList;

    /**
     * 平台
     */
    private List<String> platformList;

    /**
     * 资产类型
     */
    private List<List<String>> resourceTypeList;

    /**
     * 风险状态
     */
    private String status;

    /**
     * 忽略类型 MISREPORT, EXCEPTION, IGNORE;
     */
    private List<String> ignoreReasonTypeList;

    /**
     * 规则类型id列表
     */
    private List<List<Long>> ruleTypeIdList;

    /**
     * 风险创建的开始时间
     */
    private String gmtCreateStart;

    /**
     * 风险创建的结束时间
     */
    private String gmtCreateEnd;

    /**
     * 风险更新的开始时间
     */
    private String gmtModifiedStart;

    /**
     * 风险更新的结束时间
     */
    private String gmtModifiedEnd;

    /**
     * 资产状态 eg: exist, not_exist
     */
    private String resourceStatus;

}
