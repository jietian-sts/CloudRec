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

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AgentRegistryPO {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String platform;

    private String registryValue;

    private Date registryTime;

    private String cron;

    private String status;

    private String agentName;

    private String cloudAccountId;

    private String secretKey;

    private String persistentToken;

    private String onceToken;

    private String healthStatus;

}