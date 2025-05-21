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
package com.alipay.application.service.resource.identitySecurity.model;

import lombok.Data;

/**
 * Date: 2025/4/18
 * Author: lz
 * desc: 云资产 - 账户信息解析实体
 */
@Data
public class ResourceUserInfoDTO {

    /**
     * 账户名
     */
    private String userName;

    /**
     * 账户id
     */
    private String userId;

    /**
     * 平台
     */
    private String platform;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 创建时间
     */
    private String updateDate;

    /**
     * 最后登陆时间
     */
    private String lastLoginDate;

    /**
     * MFA状态
     */
    private boolean MFAStatus;




}
