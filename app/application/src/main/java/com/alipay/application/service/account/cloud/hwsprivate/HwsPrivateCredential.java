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
package com.alipay.application.service.account.cloud.hwsprivate;


import com.alipay.application.service.account.cloud.Credential;
import lombok.Getter;

import java.util.List;

/*
 *@title HwsPrivateCredential
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/12/25 10:18
 */
@Getter
public class HwsPrivateCredential implements Credential {

    private String ak;

    private String sk;

    private String regionId;

    private String projectId;

    private String endpoint;

    private String iamEndpoint;

    private String vpcEndpoint;

    private String elbEndpoint;

    private String evsEndpoint;

    private String ecsEndpoint;

    private String obsEndpoint;


    @Override
    public boolean verification() {
        return true;
    }

    @Override
    public List<Region> regions() {
        return List.of();
    }
}
