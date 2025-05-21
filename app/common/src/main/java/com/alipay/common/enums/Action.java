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
 *@title Action
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/8/21 10:14
 */
public class Action {

    public enum RiskAction {
        ADD_NOTE("ADD_NOTE", "添加备注"), IGNORE_RISK("IGNORE_RISK", "忽略风险"),
        CANCEL_IGNORE_RISK("CANCEL_IGNORE_RISK", "取消忽略风险"), TRIGGER_SCAN("TRIGGER_SCAN", "触发扫描"),
        TEST_RULE("TEST_RULE", "触发规则测试"), CREATE_RULE("CREATE_RULE", "创建规则"), REAPPEAR("REAPPEAR", "风险复现"),
        REPAIRED("REPAIRED", "风险修复"),
        WHITED("WHITED", "风险加白"),
        CANCEL_WHITED("CANCEL_WHITED", "取消风险加白"),
        ;

        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        RiskAction(String code, String name) {

            this.code = code;
            this.name = name;
        }
    }

}
