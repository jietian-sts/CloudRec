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
import lombok.Data;

/*
 *@title SaveTenantRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/13 17:48
 */
@Data
public class SaveTenantRequest {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 租户名称
     */
    @NotEmpty(message = "租户名称不能为空")
    private String tenantName;

    /**
     * 租户状态
     */
    @NotEmpty(message = "租户状态不能为空")
    private String status;

    /**
     * 租户描述
     */
    @NotEmpty(message = "租户描述不能为空")
    private String tenantDesc;

}
