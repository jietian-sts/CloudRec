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
package com.alipay.application.service.account.cloud.gcp;


import com.alipay.application.service.account.cloud.Credential;
import lombok.Getter;

import java.util.List;

/*
 *@title GcpCredential
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/12/25 10:17
 */
@Getter
public class GcpCredential implements Credential {

    private String credential;


    @Override
    public boolean verification() {
        return true;
    }

    @Override
    public List<Region> regions() {
        return List.of();
    }
}
