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
package com.alipay.application.share.request.account;


import com.alipay.application.service.account.enums.SecurityProductStatus;
import com.alipay.application.service.account.enums.SecurityProductType;
import com.alipay.application.share.request.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/*
 *@title GetCloudAccountSecurityProductPostureListRequest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 11:40
 */
@Getter
@Setter
public class GetCloudAccountSecurityProductPostureListRequest extends BaseRequest {

    /**
     * 云平台
     */
    private String platform;

    /**
     * 云账号id
     */
    private String cloudAccountId;


    /**
     * 状态map
     * key: 云产品枚举
     * <{@link SecurityProductType }>
     * value: 云产品类型
     * <{@link SecurityProductStatus }>
     */
    private Map<String, String> statusMap;


}
