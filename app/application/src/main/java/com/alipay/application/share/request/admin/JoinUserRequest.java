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

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 *@title JoinUser
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/14 09:23
 */
@Data
public class JoinUserRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "userId不能为空")
    private String userId;

    /**
     * 租户ID
     */
    @NotNull(message = "tenantId不能为空")
    private Long tenantId;
}
