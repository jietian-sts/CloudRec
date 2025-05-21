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
package com.alipay.application.service.system.utils;

import com.alipay.application.share.vo.ApiResponse;
import com.alipay.dao.mapper.OpenApiAuthMapper;
import com.alipay.dao.po.OpenApiAuthPO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Send the secretKey to the calling system for the calling system to call the portal system interface for authentication.
 * The request headers for calling the system include App-Key (calling system name), Timestamp (timestamp), App-Sign
 * appSign generation rules: DigestUtils.md5Hex(accessKey + timestamp + secretKey)
 * <p>
 * After receiving the request, parse the request header field and generate appSign locally,
 * Rule: DigestUtils.md5Hex(accessKey + timestamp + secretKey)
 * Then compare whether the two are the same, if they are the same, they are released, if they are different, they are truncated.
 */
@Component
public class DigestSignUtils {

    @Resource
    private OpenApiAuthMapper openApiAuthMapper;

    public static final String Timestamp = "timestamp";

    public static final String accessKeyName = "access-key";

    public static final String sign = "sign";

    public ApiResponse<String> isAuth(HttpServletRequest request) {
        Map<String, String> headerMap = getHeadersInfo(request);
        if (headerMap.isEmpty()) {
            return new ApiResponse<>(ApiResponse.ACCESS_DENIED, "header param is empty");
        }

        String timeStamp = MapUtils.getString(headerMap, Timestamp);
        String accessKey = MapUtils.getString(headerMap, accessKeyName);
        String appSign = MapUtils.getString(headerMap, sign);
        if (StringUtils.isEmpty(timeStamp) || StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(appSign)) {
            return new ApiResponse<>(ApiResponse.ACCESS_DENIED, "Timestamp or access-key or sign is empty");
        }

        OpenApiAuthPO openApiAuthPO = openApiAuthMapper.findByAccessKey(accessKey);
        if (Objects.isNull(openApiAuthPO)) {
            return new ApiResponse<>(ApiResponse.ACCESS_DENIED, "accessKey does not exist");
        }

        if (Strings.isBlank(openApiAuthPO.getSecretKey())) {
            return new ApiResponse<>(ApiResponse.ACCESS_DENIED, "secret-key is empty");
        }

        //accessKey+timestamp+local secretkey generates sign
        //Get systemSecretKey from the database based on accessKey
        String systemSecretKey = openApiAuthPO.getSecretKey();
        String realAppSign = DigestUtils.md5Hex(accessKey + timeStamp + systemSecretKey);

        //If the comparison is successful, it will be released.
        if (StringUtils.equals(appSign, realAppSign)) {
            return ApiResponse.SUCCESS;
        }

        return ApiResponse.FAIL;
    }

    /**
     * Get the request header field key-value
     *
     * @param request req
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        /*
         *     Enumeration keys costs 6 milliseconds
         * 　　Enumeration elements costs 5 milliseconds
         * 　　Iterator keySet costs 10 milliseconds
         * 　　Iterator entrySet costs 10 milliseconds
         */
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public static String generateKey(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
