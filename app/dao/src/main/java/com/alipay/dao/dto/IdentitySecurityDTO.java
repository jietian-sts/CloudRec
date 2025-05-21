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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Date: 2025/4/23
 * Author: lz
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentitySecurityDTO extends PageDTO {

    /**
     * 云账号
     */
    private String cloudAccountId;

    /**
     * 标签id,逗号分隔
     */
    private List<String> tags;

    /**
     * 规则id
     */
    private String ruleId;

    /**
     * 规则id,逗号分隔
     */
    private List<String> ruleIds;

    /**
     * 平台
     */
    private String platform;

    /**
     * 平台列表
     */
    private List<String> platformList;

    /**
     * accessKeyId列表
     */
    private List<String> accessKeyIdList;
}
