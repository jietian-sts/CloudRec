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
package com.alipay.application.service.resource.identitySecurity.model;

import lombok.Data;

/**
 * Date: 2025/4/18
 * Author: lz
 * desc: 云资产 - 账户信息解析实体
 */
@Data
public class ResourcePolicyInfoDTO {
    /**
     * 资源名称
     */
    private String policyName;

    /**
     * 策略类型
     */
    private String policyType;

    /**
     * 资源来源
     */
    private String source;

    /**
     * 最近使用时间
     */
    private String lastUsed;

    /**
     * 风险等级
     */
    private String riskLevel;

    /**
     * 策略描述
     */
    private String description;

    /**
     * 策略详情
     */
    private String policyDocument;


}