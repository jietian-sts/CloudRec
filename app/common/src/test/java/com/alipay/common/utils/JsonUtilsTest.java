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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilsTest {

    /**
     * [单测用例]测试场景：输入为有效的JSON字符串
     */
    @Test
    public void testIsValidJson_ValidJson() {
        String validJson = "{\"key\":\"value\"}";
        assertTrue(JsonUtils.isValidJson(validJson));
    }

    /**
     * [单测用例]测试场景：输入为无效的JSON字符串
     */
    @Test
    public void testIsValidJson_InvalidJson() {
        String invalidJson = "{\"key\":\"value\"";
        assertFalse(JsonUtils.isValidJson(invalidJson));
    }

    /**
     * [单测用例]测试场景：输入为空字符串
     */
    @Test
    public void testIsValidJson_EmptyString() {
        String emptyString = "";
        assertFalse(JsonUtils.isValidJson(emptyString));
    }

    /**
     * [单测用例]测试场景：输入为null
     */
    @Test
    public void testIsValidJson_Null() {
        String nullString = null;
        assertFalse(JsonUtils.isValidJson(nullString));
    }

}
