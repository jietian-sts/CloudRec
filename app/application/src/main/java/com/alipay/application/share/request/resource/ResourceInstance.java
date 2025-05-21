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

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/*
 *@title ResourceInstance
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/8 16:16
 */
@Getter
@Setter
public class ResourceInstance {

    private String id;

    private String updateTime;

    private String version;

    /**
     * 供应商
     */
    private String platform;
    /**
     * 云账户id
     */
    private String cloudAccountId;

    /**
     * 云账号别名
     */
    private String alias;

    /**
     * 实例类型
     */
    private String resourceType;

    /**
     * 数据所属的类型
     */
    private String resourceGroupType;

    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 资源id
     */
    private String resourceId;

    private Boolean inChina;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 区域
     */
    private String regionId;

    /**
     * 地址
     */
    private String address;

    /**
     * 实例对象
     */
    private Map<String, Object> instance;
}
