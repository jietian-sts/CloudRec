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
package com.alipay.application.share.request.openapi;


import com.alipay.dao.dto.BaseScrollDTO;
import lombok.Getter;
import lombok.Setter;

/*
 *@title QueryResourceRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/2 18:21
 */
@Getter
@Setter
public class QueryResourceRequest extends BaseScrollDTO {

    /**
     * 云平台
     */
    private String platform;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 云账号id
     */
    private String cloudAccountId;

    /**
     * 资源id
     */
    private String resourceId;

}
