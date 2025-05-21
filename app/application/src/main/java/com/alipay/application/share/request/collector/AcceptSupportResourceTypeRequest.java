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
package com.alipay.application.share.request.collector;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
 *@title AcceptSupportResourceTypeRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/12 10:22
 */
@Getter
@Setter
public class AcceptSupportResourceTypeRequest {

    /**
     * 平台
     */
    private String platform;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 注册值
     */
    private String registryValue;

    /**
     * 支持的资源类型
     */
    private List<Resource> resourceList;

    @Getter
    @Setter
    public static class Resource {
        private String resourceType;

        private String resourceTypeName;

        private String resourceGroupType;
    }

}
