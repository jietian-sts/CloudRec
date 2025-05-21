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

import lombok.Data;

import java.util.Date;

@Data
public class RuleScanResultPO {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private Long ruleId;

    private String cloudAccountId;

    private String resourceId;

    private String resourceName;

    private String updateTime;

    private String platform;

    private String resourceType;

    private String region;

    private Long tenantId;

    private Long version;

    private String status;

    private String result;

    /**
     * 规则快照
     */
    private String ruleSnapshoot;

    /**
     * 资产快照
     */
    private String resourceSnapshoot;

    /**
     * 忽略的原因类型
     */
    private String ignoreReasonType;

    /**
     * 忽略的原因
     */
    private String ignoreReason;

    /**
     * 是否为新风险 0:否 1:是
     */
    private Integer isNew;

    /**
     * 关联资产表id
     */
    private Long cloudResourceInstanceId;

    /**
     * 资源状态
     */
    private String resourceStatus;

    /**
     * 关联白名单id
     */
    private Long whitedId;
}