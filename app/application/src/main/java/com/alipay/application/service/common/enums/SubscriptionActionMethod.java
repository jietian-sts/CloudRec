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
package com.alipay.application.service.common.enums;

import lombok.Getter;

/*
 *@title SubcriptionConfigType
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/10 17:04
 */
@Getter
public enum SubscriptionActionMethod {

    DING("DING", "钉钉群定时通知"),

    DING_REAL_TIME_PUSH("DING_REAL_TIME_PUSH", "钉钉群实时增量风险通知"),

    REAL_TIME_PUSH("REAL_TIME_PUSH", "接口实时增量通知");

    private String code;
    private String desc;

    SubscriptionActionMethod(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
