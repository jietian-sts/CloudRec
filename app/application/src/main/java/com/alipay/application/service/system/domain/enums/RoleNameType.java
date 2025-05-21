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
package com.alipay.application.service.system.domain.enums;

/*
 *@title RoleNameType
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/14 12:08
 */
public enum RoleNameType {
    user, admin;

    public static boolean exist(String roleName) {
        for (RoleNameType roleNameType : RoleNameType.values()) {
            if (roleNameType.name().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public static RoleNameType getRole(String roleName) {
        for (RoleNameType roleNameType : RoleNameType.values()) {
            if (roleNameType.name().equals(roleName)) {
                return roleNameType;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + roleName);
    }
}
