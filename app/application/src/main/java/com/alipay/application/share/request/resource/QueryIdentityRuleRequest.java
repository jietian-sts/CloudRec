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
package com.alipay.application.share.request.resource;

import com.alipay.application.share.request.base.BaseRequest;
import lombok.Data;


/**
 * Date: 2025/4/16
 * Author: lz
 */
@Data
public class QueryIdentityRuleRequest extends BaseRequest {

    private Long id;

    /**
     * 云平台
     */
    private String platform;

    /**
     * 云账号
     */
    private String cloudAccountId;

    /**
     * 标签id,逗号分隔
     */
    private String tags;

    /**
     * 规则id,逗号分隔
     */
    private String ruleIds;

    /**
     * AK
     */
    private String accessKeyIds;
}
