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


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 *@title RiskLevel
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/6 17:34
 */
public enum RiskLevel {
    High, Medium, Low;

    /**
     * 判断
     */
    public static boolean exist(String riskLevel) {
        if(!StringUtils.isEmpty(riskLevel)){
            for (RiskLevel riskLevelEnum : RiskLevel.values()) {
                if (riskLevelEnum.name().equals(riskLevel)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> list() {
        return Stream.of(RiskLevel.values()).map(RiskLevel::name).collect(Collectors.toList());
    }
}
