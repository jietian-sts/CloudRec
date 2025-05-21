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

/*
 *@title JsonUtils
 *@description
 *@author jietian
 *@version 1.0
 *@create 2023/10/18 12:51
 */

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtils {

    public static boolean isValidJson(String jsonString) {
        if (StringUtils.isBlank(jsonString)){
            return false;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }


    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }


    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        return JSON.parseArray(jsonString, clazz);
    }


    public static Map<String, Object> parseMap(String jsonString) {
        return JSON.parseObject(jsonString, new TypeReference<>() {
        });
    }


    public static Object getValueFromJson(String jsonString, String key) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return jsonObject.get(key);
    }


    public static int getFieldSize(String jsonString, String key) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        Object value = jsonObject.get(key);

        if (value == null) {
            return 0;
        } else if (value instanceof JSONArray jsonArray) {
            return jsonArray.size();
        } else {
            return 1;
        }
    }
}
