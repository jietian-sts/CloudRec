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
package com.alipay.application.service.rule.domain.repo;

/*
 *@title OpaServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/5 17:16
 */

import com.alibaba.fastjson.JSON;
import com.bisnode.opa.client.OpaClient;
import com.bisnode.opa.client.data.OpaDocument;
import com.bisnode.opa.client.policy.OpaPolicy;
import com.bisnode.opa.client.query.QueryForDocumentRequest;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
class OpaRepositoryImpl implements OpaRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpaRepositoryImpl.class);


    @Resource
    private OpaClient client;


    @Override
    public String createOrUpdatePolicy(String policyContent) {
        // 从 policyContent 解析
        String regoPackage = findPackage(policyContent);

        OpaPolicy opaPolicy = new OpaPolicy(regoPackage, policyContent);

        try {
            client.createOrUpdatePolicy(opaPolicy);
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    @Override
    public void createOrUpdatePolicy(String path, String policyContent) {
        OpaPolicy opaPolicy = new OpaPolicy(path, policyContent);
        try {
            client.createOrUpdatePolicy(opaPolicy);
        } catch (Exception e) {
            LOGGER.error("createOrUpdatePolicy error:{}, policyContent:{}", e, policyContent);
        }
    }

    @Override
    public void upsertData(String path, Object data) {
        OpaDocument opaDocument = new OpaDocument(path, JSON.toJSONString(data));
        try {
            client.createOrOverwriteDocument(opaDocument);
        } catch (Exception e) {
            LOGGER.info("upsertData error:{}", e.getMessage());
        }
    }

    @Override
    public String getPolicy(String path) {
        return null;
    }

    @Override
    public Map callOpa(String policyContent, String jsonInputStr) {
        if (policyContent.contains("http.send")) {
            throw new RuntimeException("函数 http.send 当前暂不支持");
        }
        Object obj = JSON.parse(jsonInputStr);
        String aPackage = findPackage(policyContent);
        Map resp = new HashMap<>();
        try {
            QueryForDocumentRequest ageRequest = new QueryForDocumentRequest(obj, aPackage);
            resp = client.queryForDocument(ageRequest, Map.class);
        } catch (Exception e) {
            resp.put("error", e.getMessage());
            resp.put("risk", false);
        }

        LOGGER.info("resp:{}", resp);
        return resp;
    }

    @Override
    public Map callOpa(String path, String policyContent, String jsonInputStr) {
        if (policyContent.contains("http.send")) {
            throw new RuntimeException("The function http.send is not currently supported");
        }
        Object obj = JSON.parse(jsonInputStr);
        Map resp = new HashMap<>();
        try {
            QueryForDocumentRequest ageRequest = new QueryForDocumentRequest(obj, path);
            resp = client.queryForDocument(ageRequest, Map.class);
        } catch (Exception e) {
            resp.put("error", e.getMessage());
            resp.put("risk", false);
        }

        LOGGER.info("resp:{}", resp);
        return resp;
    }

    public String findPackage(String policyContent) {
        String pattern = "package\\s+([\\w.]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(policyContent);

        if (m.find()) {
            return m.group(1);
        }

        throw new RuntimeException("package not found");
    }

    @Override
    public String findWhitedConfigPackage(String policyContent, String whitedConfigId) {
        String pattern = "package\\s+([\\w.]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(policyContent);

        if (m.find()) {
            return m.group(1) + "_" + whitedConfigId;
        }

        throw new RuntimeException("package not found");
    }


}
