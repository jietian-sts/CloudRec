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
package com.alipay.common.enums;

import lombok.Getter;

/*
 *@title ResourceGroupType
 *@description Enumeration of resource group types
 *@author jietian
 *@version 1.0
 *@create 2024/8/30 12:09
 */
@Getter
public enum ResourceGroupType {

    UNKNOWN("UNKNOWN", "未知", ""),
    NET("NET", "网络", "icon/net.svg"),
    CONTAINER("CONTAINER", "容器", "icon/container.svg"),
    DATABASE("DATABASE", "数据库", "icon/database.svg"),
    STORE("STORE", "存储", "icon/store.svg"),
    COMPUTE("COMPUTE", "计算", "icon/compute.svg"),
    IDENTITY("IDENTITY", "身份", "icon/identity.svg"),
    CONFIG("CONFIG", "配置", "icon/config.svg"),
    SECURITY("SECURITY", "安全产品", "icon/security.svg"),
    AI("AI", "AI", "icon/AI.svg"),
    MIDDLEWARE("MIDDLEWARE", "中间件", "icon/middleware.svg"),
    BIGDATA("BIGDATA", "大数据", "icon/bigdata.svg"),
    LOG("LOG", "日志", "icon/log.svg"),
    GOVERNANCE("GOVERNANCE", "管理", "icon/governance.svg");

    private final String code;
    private final String desc;
    private final String icon;

    ResourceGroupType(String code, String desc, String icon) {
        this.code = code;
        this.desc = desc;
        this.icon = icon;
    }

    public static ResourceGroupType getByCode(String code) {
        for (ResourceGroupType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
