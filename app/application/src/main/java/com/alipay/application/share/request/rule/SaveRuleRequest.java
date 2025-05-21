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
package com.alipay.application.share.request.rule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/*
 *@title SaveRuleRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/6 17:43
 */
@Data
public class SaveRuleRequest {

    private Long id;
    /**
     * 规则组id
     */
    private List<Long> ruleGroupIdList;

    /**
     * 规则名称
     */
    @NotNull(message = "规则名称不能为空")
    private String ruleName;

    /**
     * 风险等级 High,Medium,Low
     */
    @NotNull(message = "风险等级不能为空")
    private String riskLevel;

    /**
     * 平台
     */
    @NotNull(message = "平台不能为空")
    private String platform;

    /**
     * 资源类型
     */
    @NotEmpty(message = "资源类型不能为空")
    private List<String> resourceType;

    /**
     * 规则描述
     */
    @NotEmpty(message = "规则描述不能为空")
    private String ruleDesc;

    @NotEmpty(message = "规则状态不能为空")
    private String status;

    /**
     * 规则上下文
     */
    private String context;

    /**
     * 规则建议
     */
    private String advice;

    /**
     * 修复文档链接
     */
    private String link;

    /**
     * rego 规则代码
     */
    @NotBlank(message = "规则代码不能为空")
    private String ruleRego;

    /**
     * 规则关联的类型id列表
     */
    @NotNull(message = "规则关联的类型id列表不能为空")
    private List<List<Long>> ruleTypeIdList;

    /**
     * 关联数据
     */
    private List<LinkDataParam> linkedDataList;

    /**
     * 全局变量配置id列表
     */
    private List<Long> globalVariableConfigIdList;
}
