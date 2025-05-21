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
package com.alipay.application.service.resource.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Date: 2025/4/21
 * Author: lz
 */
@Getter
@AllArgsConstructor
public enum IdentityTagConfig {

    ConsoleLoginMethod_SSO("SSO", "ConsoleLoginMethod认证方式SSO"),
    ConsoleLoginMethod_Password("Password",  "ConsoleLoginMethod认证方式password"),
    MFA("MFA", "MFA认证方式开启"),
    NO_ACL("NO_ACL", "无ACL权限"),
    INACTIVE("INACTIVE", "用户长时间未登陆"),
    ;

    private String tagName;

    private String tagDesc;
}
