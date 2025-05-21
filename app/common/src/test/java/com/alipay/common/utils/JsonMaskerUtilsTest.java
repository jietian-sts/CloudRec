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

import org.junit.Assert;
import org.junit.Test;

public class JsonMaskerUtilsTest {

    //[单测用例]测试场景：测试正常情况，输入为普通的json字符串,将字符串中的String替换为同等长度的随机字符，而数字不替换
    @Test
    public void testMaskSensitiveData_Normal() throws Exception {
        String json = "{\"name\":\"Jack\",\"age\":20}";
        String result = JsonMaskerUtils.maskSensitiveData(json);
        Assert.assertNotNull(result);

    }

    //[单测用例]测试场景：测试输入为null的情况
    @Test(expected = Exception.class)
    public void testMaskSensitiveData_Null() throws Exception {
        String json = null;
        JsonMaskerUtils.maskSensitiveData(json);
    }

    //[单测用例]测试场景：测试输入为空字符串的情况
    @Test
    public void testMaskSensitiveData_Empty() throws Exception {
        String json = "";
        String result = JsonMaskerUtils.maskSensitiveData(json);
        Assert.assertNull(result);
    }

    //[单测用例]测试场景：测试输入为非json格式的字符串
    @Test(expected = Exception.class)
    public void testMaskSensitiveData_NonJson() throws Exception {
        String json = "This is not a json string";
        JsonMaskerUtils.maskSensitiveData(json);
    }

    //[单测用例]测试场景：测试输入为包含敏感信息的json字符串
    @Test
    public void testMaskSensitiveData_Sensitive() throws Exception {
        String json = "{\"name\":\"Jack\",\"age\":20,\"password\":\"123456\"}";
        String result = JsonMaskerUtils.maskSensitiveData(json);
        Assert.assertNotNull(result);
    }
}
