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
 *@title SubscriptionDTO
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/10 17:00
 */
@Getter
@Setter
public class SubscriptionDTO extends PageDTO {

    private Long id;

    /**
     * 订阅名称
     */
    private String name;

    /**
     * status
     */
    private String status;

    /**
     * 条件
     */
    private String condition;

    /**
     * 配置的风险规则
     */
    private List<Subscription.Config> ruleConfigList;

    /**
     * 处置动作
     */
    private List<Subscription.Action> actionList;

    /**
     * 配置的规则json
     */
    private String ruleConfigJson;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 用户id
     */
    private String userId;
}
