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
package com.alipay.application.service.resource.identitySecurity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.application.service.resource.enums.IdentitySecurityConfig;
import com.alipay.application.service.resource.enums.ResourceVisitType;
import com.alipay.application.service.resource.identitySecurity.model.ResourceAccessInfoDTO;
import com.alipay.application.service.resource.identitySecurity.model.ResourcePolicyInfoDTO;
import com.alipay.application.service.resource.identitySecurity.model.ResourceUserInfoDTO;
import com.alipay.dao.po.IdentitySecurityPO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2025/4/18
 * Author: lz
 */
@Component
public class GCPServiceAccountParse implements CloudResourceInfoParser {

    @Override
    public IdentitySecurityPO parse(IdentitySecurityPO identitySecurityPO, String resourceInstance) {
        identitySecurityPO.setResourceTypeGroup(IdentitySecurityConfig.GCP_IAM_ServiceAccount.getResourceTypeGroup());
        identitySecurityPO.setPlatform(IdentitySecurityConfig.GCP_IAM_ServiceAccount.getPlatformType());
        identitySecurityPO.setAccessInfos(JSON.toJSONString(parseAccessInfo(resourceInstance)));
        identitySecurityPO.setAccessType(String.join(",", getVisitTypes(resourceInstance)));
        identitySecurityPO.setUserInfo(JSON.toJSONString(parseUserInfo(resourceInstance)));
        identitySecurityPO.setPolicies(JSON.toJSONString(parsePolicyInfo(resourceInstance)));
        return identitySecurityPO;
    }

    @Override
    public ResourceUserInfoDTO parseUserInfo(String resourceInstance) {
        JSONObject jsonObject = coverResourceInstanceStr(resourceInstance);
        ResourceUserInfoDTO resourceUserInfoDTO = new ResourceUserInfoDTO();
        if (jsonObject.get("ServiceAccount") != null) {
            JSONObject serviceAccount = jsonObject.getJSONObject("ServiceAccount");
            resourceUserInfoDTO.setUserName(serviceAccount.getString("displayName"));
            resourceUserInfoDTO.setUserId(serviceAccount.getString("uniqueId"));
            resourceUserInfoDTO.setEmail(serviceAccount.getString("email"));
            resourceUserInfoDTO.setPlatform(IdentitySecurityConfig.GCP_IAM_ServiceAccount.getPlatformType());
        }
        return resourceUserInfoDTO;
    }

    @Override
    public List<ResourcePolicyInfoDTO> parsePolicyInfo(String resourceInstance) {
        //暂无
        return null;
    }

    @Override
    public List<ResourceAccessInfoDTO> parseAccessInfo(String resourceInstance) {
        JSONObject jsonObject = coverResourceInstanceStr(resourceInstance);
        List<ResourceAccessInfoDTO> resourceAccessInfoDTOS = new ArrayList<>();
        Object keys = jsonObject.get("Keys");
        if (keys != null) {
            List<JSONObject> keyList = JSON.parseArray(JSON.toJSONString(keys), JSONObject.class);
            for (JSONObject key : keyList) {
                ResourceAccessInfoDTO resourceAccessInfoDTO = new ResourceAccessInfoDTO();
                resourceAccessInfoDTOS.add(resourceAccessInfoDTO);
                if (StringUtils.isNotBlank(key.getString("name"))) {
                    String accessKeyId = getKey(key.getString("name"));
                    resourceAccessInfoDTO.setAccessKeyId(accessKeyId);
                    resourceAccessInfoDTO.setVisitTypes(getVisitTypes(resourceInstance));
                    resourceAccessInfoDTOS.add(resourceAccessInfoDTO);
                }
            }
        }
        return resourceAccessInfoDTOS;
    }

    private String getKey(String input) {
        //正则表达式匹配字符串中的数字
        String regex = "/keys/([a-f0-9]+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public List<String> getVisitTypes(String resourceInstance) {
        //GCP平台下默认只支持API访问
        return Arrays.asList(ResourceVisitType.API.name());
    }

    @Override
    public List<String> parseTags(String resourceInstance, String ruleIds, String cloudAccountId, String resourceId) {
        return null;
    }


    private JSONObject coverResourceInstanceStr(String resourceInstanceStr) {
        if (StringUtils.isBlank(resourceInstanceStr)) {
            return null;
        }
        return JSON.parseObject(resourceInstanceStr);
    }
}
