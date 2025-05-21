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
///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.alipay.application.service.rule.domain.repo;
///*
// *@title OpaServiceImpl
// *@description
// *@author jietian
// *@version 1.0
// *@create 2024/6/5 17:16
// */
//
//import com.alibaba.fastjson.JSON;
//import com.alipay.application.service.rule.domain.repo.client.OpaClient;
//import com.alipay.application.service.rule.domain.repo.client.api.DataApi;
//import com.alipay.application.service.rule.domain.repo.client.api.PolicyApi;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class OpaRepositoryImpl implements OpaRepository {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(OpaRepositoryImpl.class);
//
//    @Value("${opa.url}")
//    private String OPA_URL;
//
//    @Override
//    public String createOrUpdatePolicy(String policyContent) {
//        String regoPackage = findPackage(policyContent);
//
//        OpaClient client = OpaClient.builder().url(OPA_URL)
//                .build();
//
//        CompletableFuture<PolicyApi.UpsertPolicyResponse> result = client
//                .upsertPolicy(regoPackage, policyContent);
//
//        try {
//            result.join();
//        } catch (Exception e) {
//            LOGGER.info("createOrUpdatePolicy error:{}", e.getCause().getMessage());
//            return e.getCause().getMessage();
//        }
//        return null;
//    }
//
//    @Override
//    public void upsertData(String path, Object data) {
//        OpaClient client = OpaClient.builder().url(OPA_URL)
//                .build();
//
//        CompletableFuture<DataApi.UpsertDataResult> upsertDataResultCompletableFuture = client.upsertData(path, data);
//
//        try {
//            upsertDataResultCompletableFuture.get();
//        } catch (Exception e) {
//            LOGGER.info("upsertData error:{}", e.getMessage());
//        }
//    }
//
//    @Override
//    public String getPolicy(String id) {
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> callOpa(String policyContent, String jsonInputStr) {
//        if (policyContent.contains("http.send")) {
//            throw new RuntimeException("The function http.send is not currently supported");
//        }
//
//        OpaClient client = OpaClient.builder().url(OPA_URL).build();
//
//        try {
//            DataApi.GetDataWithInputResponse response = client.getData(findPackage(policyContent), JSON.parseObject(jsonInputStr, Map.class), DataApi.GetDataWithInputResponse.class).join();
//            Object o = response.get("result");
//            if (o instanceof Map<?, ?>) {
//                return (Map<String, Object>) o;
//            }
//            throw new RuntimeException("The result is not a Map");
//        } catch (Exception e) {
//            Map<String, Object> resp = new HashMap<>();
//            resp.put("error", e.getMessage());
//            resp.put("risk", false);
//            return resp;
//        }
//
//    }
//
//    public String findPackage(String policyContent) {
//        String pattern = "package\\s+([\\w.]+)";
//
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(policyContent);
//
//        if (m.find()) {
//            return m.group(1);
//        }
//
//        throw new RuntimeException("package not found");
//    }
//}
