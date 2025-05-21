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
package com.alipay.common.utils;

import java.util.Arrays;
import java.util.List;

/*
 *@title CheckSortParamField
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/12/6 16:21
 */
public class PreventingSQLJoint {

    public static void checkSortParamField(String field, List<String> allowFields) {
        // Preventing SQL injection
        if (field != null && !field.trim().isEmpty()) {
            if (!allowFields.contains(field)) {
                throw new IllegalArgumentException("sortParam is illegal");
            }
        }
    }

    public static void checkSortTypeField(String field) {
        if (field != null && !field.trim().isEmpty()) {
            if (!Arrays.asList("ASC", "DESC", "asc", "desc").contains(field)) {
                throw new IllegalArgumentException("sortType is illegal");
            }
        }
    }
}
