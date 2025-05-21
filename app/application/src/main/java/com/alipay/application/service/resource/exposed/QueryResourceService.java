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
package com.alipay.application.service.resource.exposed;


import jakarta.validation.constraints.NotEmpty;

/*
 *@title QueryResourceService
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/19 15:50
 */
public interface QueryResourceService {

    /**
     * Query the example data of an asset based on the cloud platform and asset type
     *
     * @param platform cloud platforms, such as ALI_CLOUD, TENCENT_CLOUD, BAIDU_CLOUD, etc.
     * @param resourceType Asset type, such as ECS, RDS, SLB, etc.
     * @return Asset JSON
     */
    String queryExampleData(@NotEmpty String platform, @NotEmpty String resourceType);
}
