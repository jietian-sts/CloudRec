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
package com.alipay.application.share.request.admin;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/*
 *@title CreateUserRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/11/4 12:32
 */
@Getter
@Setter
public class CreateUserRequest {

    /**
     * userId
     */
    @NotEmpty(message = "userId不能为空")
    private String userId;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    @NotEmpty(message = "username不能为空")
    private String username;

    /**
     * 角色
     */
    @NotEmpty(message = "roleName不能为空")
    private String roleName;

    /**
     * 租户id
     */
    private String tenantIds;
}
