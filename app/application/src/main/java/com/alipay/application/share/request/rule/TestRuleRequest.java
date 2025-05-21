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

import lombok.Data;

import java.util.List;

/*
 *@title TestRuleRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/6 12:27
 */
@Data
public class TestRuleRequest {

    /**
     * rego规则(rego code)
     */
    private String ruleRego;

    /**
     * 输入数据
     */
    private String input;

    /**
     * 全局变量配置id列表
     */
    private List<Long> globalVariableConfigIdList;


    /**
     * 关联资产数据
     */
    private List<LinkDataParam> linkedDataList;


    /**
     * 运行的数据维度
     * 1. 全部数据
     * 2. 指定租户
     * 3. 指定云账号
     * 4. 示例数据
     */
    private String type;


    /**
     * 租户id或云账号id
     */
    private String selectId;

    /**
     * 云平台
     */
    private String platform;

    /**
     * 资源类型
     */
    private String resourceType;
}
