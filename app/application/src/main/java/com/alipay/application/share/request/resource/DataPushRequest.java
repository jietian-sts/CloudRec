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

/*
 *@title DataPushRequest
 *@description agent推送数据统一接收的数据模型
 *@author jietian
 *@version 1.0
 *@create 2023/12/19 15:19
 */

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataPushRequest {

    @NotEmpty(message = "data cannot be null")
    private String data;

    @Getter
    @Setter
    public static class Data {
        @NotEmpty(message = "version cannot be null")
        private String version;

        @NotEmpty(message = "cloudAccountId cannot be null")
        private String cloudAccountId;

        @NotEmpty(message = "resourceType cannot be null")
        private String resourceType;

        private String resourceTypeName;

        @NotEmpty(message = "resourceGroupType cannot be null")
        private String resourceGroupType;

        @NotEmpty(message = "platform cannot be null")
        private String platform;

        private String platformName;

        @NotNull(message = "resourceInstancesAll cannot be null")
        List<ResourceInstance> resourceInstancesAll;
    }
}
