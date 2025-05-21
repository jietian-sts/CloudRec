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

import java.util.List;

/*
 *@title ListUtils
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/6 15:56
 */
public class ListUtils {

    public static <T> List<T> setList(List<List<T>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.stream().map(e -> {
            if (e.isEmpty()) {
                return null;
            }
            if (e.size() == 1) {
                return e.get(0);
            } else {
                return e.get(1);
            }
        }).toList();
    }

    public static boolean isEmpty(List<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }

        // [[]]
        for (Object o : list) {
            if (o == null || (o instanceof List &&((List<?>) o).isEmpty())) {
                return true;
            }
        }

        return false;
    }
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }
}
