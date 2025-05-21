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

/*
 *@title associativeMode
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/22 10:19
 */
public enum AssociativeMode {

    ONE_TO_ONE("仅关联一次"), ONE_TO_MANY("关联多次"), MANY_TO_ONE("无关联字段");

    private String name;

    AssociativeMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static AssociativeMode getAssociativeMode(String name) {
        for (AssociativeMode associativeMode : AssociativeMode.values()) {
            if (associativeMode.getName().equals(name)) {
                return associativeMode;
            }
        }
        return null;

    }
}
