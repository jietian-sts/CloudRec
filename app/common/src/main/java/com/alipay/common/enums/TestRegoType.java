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
 *@title TestRegoType
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/14 11:16
 */
public enum TestRegoType {

    tenant("TENANT", "租户维度"),
    cloud_account("CLOUD_ACCOUNT", "云账号维度");

    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    TestRegoType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static TestRegoType getTestRegoType(String type) {
        for (TestRegoType testRegoType : TestRegoType.values()) {
            if (testRegoType.getType().equals(type)) {
                return testRegoType;
            }
        }
        return null;
    }
}
