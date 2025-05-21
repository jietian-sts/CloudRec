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
package com.alipay.application.service.common;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
 *@title NotifyTest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/2/11 15:02
 */
@RunWith(MockitoJUnitRunner.class)
class NotifyTest {

    @Test
    void parseTemplate() {
        String output= "{\n" +
                "  \"d\": \"111.111.111.111\",\n" +
                "  \"risk\": true\n" +
                "}";
        // eg :bucket {$.BucketName} 存在匿名操作风险
        assertNotNull(Notify.parseTemplate("是否存在风险:{$.risk},bucket {$.d} 存在匿名操作风险", output));
    }

    @Test
    void parseTemplate2() {
        String output= "{\n" +
                "    \"msg\": [\n" +
                "        {\n" +
                "            \"config\": \"{\\\"IngressSlbNetworkType\\\":\\\"internet\\\",\\\"IngressSlbSpec\\\":\\\"slb.s2.small\\\"}\",\n" +
                "            \"name\": \"nginx-ingress-controller\",\n" +
                "            \"version\": \"v1.9.3-aliyun.1\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"risk\": true,\n" +
                "    \"ResourceId\": \"c9498453edcad49f99579e846bacbb484\",\n" +
                "    \"ResourceName\": \"MSP-HZ-PROD\"\n" +
                "}";
        String s = Notify.parseTemplate("当前ACK集群 {$.msg[0].version}使用 xxx 版本的nginx-ingress-controller", output);
        if (Strings.isNotEmpty(s)){
            Assert.isTrue(s.contains("v1.9.3-aliyun.1"),"s:"+s);
        }else {
            throw new RuntimeException();
        }
    }

    @Test
    void parseTemplate3() {
        String output= "";
        // eg :bucket {$.BucketName} 存在匿名操作风险
        String resp = Notify.parseTemplate("是否存在风险:{$.risk},bucket {$.d} 存在匿名操作风险", output);
        // null
        Assert.isTrue(Strings.isEmpty(resp),"resp:"+resp);
    }

    @Test
    void parseTemplate4() {
        String output= "{\n" +
                "  \"d\": \"111.111.111.111\",\n" +
                "  \"risk\": true\n" +
                "}";
        // eg :bucket {$.BucketName} 存在匿名操作风险
        String resp = Notify.parseTemplate("", output);
        // resp == output
        Assert.isTrue(resp.equals(output),"resp:"+resp);
    }


}