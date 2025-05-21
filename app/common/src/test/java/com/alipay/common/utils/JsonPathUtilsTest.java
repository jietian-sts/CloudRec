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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class JsonPathUtilsTest {

    @Mock
    private Logger log;

    @Before
    public void setUp() {
        LoggerFactory.getLogger(JsonPathUtils.class);
    }

    /**
     * [单测用例]测试场景：测试正常情况
     */
    @Test
    public void testGetValue_Normal() {
        String json = "{\"name\":\"John\", \"age\":30}";
        String jsonPath = "$.name";
        Object result = JsonPathUtils.getValue(json, jsonPath);
        assertEquals("John", result);
    }

    /**
     * [单测用例]测试场景：测试PathNotFoundException异常情况
     */
    @Test
    public void testGetValue_PathNotFoundException() {
        String json = "{\"name\":\"John\", \"age\":30}";
        String jsonPath = "$.invalidPath";
        Object result = JsonPathUtils.getValue(json, jsonPath);
        assertNull(result);
    }


    /**
     * [单测用例]测试场景：测试getValue方法正常情况
     */
    @Test
    public void testGetValueWithType_Normal() {
        String json = "{\"name\":\"John\", \"age\":30}";
        String jsonPath = "$.name";
        String result = JsonPathUtils.getValue(json, jsonPath, String.class);
        assertEquals("John", result);
    }

    /**
     * [单测用例]测试场景：测试getValue方法转换失败情况
     */
    @Test
    public void testGetValueWithType_ConversionFailed() {
        String json = "{\"name\":\"John\", \"age\":30}";
        String jsonPath = "$.name";
        Integer result = JsonPathUtils.getValue(json, jsonPath, Integer.class);
        assertNull(result);
    }

    @Test
    public void testSetValueFromJson_Success() {
        String input = "{\n" +
                "  \"AuthSummary\" : {\n" +
                "    \"AllowUpgradePartialBuy\" : 1,\n" +
                "    \"AllowUserUnbind\" : 0,\n" +
                "    \"DefaultAuthToAll\" : 0,\n" +
                "    \"RequestId\" : \"7702DB17-3F0C-511C-8000-F4EF70B13436\",\n" +
                "    \"VersionSummary\" : [ {\n" +
                "      \"AuthBindType\" : \"CORE\",\n" +
                "      \"TotalCount\" : 114000,\n" +
                "      \"UnUsedCount\" : 1860,\n" +
                "      \"Version\" : 7,\n" +
                "      \"TotalEcsAuthCount\" : 2230,\n" +
                "      \"TotalCoreAuthCount\" : 114000,\n" +
                "      \"UnusedEcsAuthCount\" : 0,\n" +
                "      \"Index\" : 5,\n" +
                "      \"UnusedCoreAuthCount\" : 1860,\n" +
                "      \"UsedCoreCount\" : 112140,\n" +
                "      \"UsedEcsCount\" : 5866\n" +
                "    } ],\n" +
                "    \"HighestVersion\" : 7,\n" +
                "    \"IsMultiVersion\" : 0,\n" +
                "    \"AllowPartialBuy\" : 1,\n" +
                "    \"HasPreBindSetting\" : false,\n" +
                "    \"Machine\" : {\n" +
                "      \"UnBindEcsCount\" : 102,\n" +
                "      \"RiskEcsCount\" : 5262,\n" +
                "      \"RiskCoreCount\" : 96731,\n" +
                "      \"TotalCoreCount\" : 107641,\n" +
                "      \"BindEcsCount\" : 5601,\n" +
                "      \"BindCoreCount\" : 104089,\n" +
                "      \"TotalEcsCount\" : 5703,\n" +
                "      \"UnBindCoreCount\" : 3552\n" +
                "    },\n" +
                "    \"AutoBind\" : 1\n" +
                "  },\n" +
                "  \"CloudAccountId\" : \"1212121042863399\"\n" +
                "}";

        String version = JsonPathUtils.getValue(input, "$.AuthSummary.HighestVersion", String.class);
        assertEquals("7", version);
    }
}
