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

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
 *@title HomeTopRiskVO
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/11 14:58
 */
@Getter
@Setter
public class HomeTopRiskDTO {

    /**
     * 平台
     */
    private String platform;

    /**
     * 规则ID
     */
    private Long ruleId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * ruleCode
     */
    private String ruleCode;

    /**
     * 规则类型
     */
    private List<String> ruleTypeNameList;

    /**
     * 风险等级
     */
    private String riskLevel;

    /**
     * 风险数量
     */
    private String count;
}
