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

import com.alipay.common.constant.JwtConstants;
import com.alipay.dao.mapper.SecretKeyMapper;
import com.alipay.dao.po.SecretKeyPO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;

@Component
public class SecretKeyUtil {

    public static String JWT_SECRET_KEY_ENCRYPT_VALUE;
    public static String ACCESS_KEY_SECRET_KEY_ENCRYPT_VALUE;

    @Resource
    private SecretKeyMapper secretKeyMapper;

    // CHARACTER POOL
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 24;

    @PostConstruct
    public synchronized void initKey() {
        SecretKeyPO jwtSecretKeyPO = secretKeyMapper.find(JwtConstants.JWT_SECRET_KEY_ENCRYPT_NAME);

        if (jwtSecretKeyPO == null) {
            String secretValue = generateRandomString(LENGTH);
            JWT_SECRET_KEY_ENCRYPT_VALUE = secretValue;

            SecretKeyPO secretKeyPO = new SecretKeyPO();
            secretKeyPO.setSecretKeyName(JwtConstants.JWT_SECRET_KEY_ENCRYPT_NAME);
            secretKeyPO.setSecretKeyValue(secretValue);
            secretKeyPO.setGmtCreate(new Date());
            secretKeyPO.setGmtModified(new Date());
            secretKeyMapper.insertSelective(secretKeyPO);
        } else {
            JWT_SECRET_KEY_ENCRYPT_VALUE = jwtSecretKeyPO.getSecretKeyValue();
        }

        SecretKeyPO accessKeySecretKeyPO = secretKeyMapper
                .find(JwtConstants.ACCESS_KEY_SECRET_KEY_ENCRYPT_NAME);
        if (accessKeySecretKeyPO == null) {
            String secretValue = generateRandomString(LENGTH);
            ACCESS_KEY_SECRET_KEY_ENCRYPT_VALUE = secretValue;
            SecretKeyPO secretKeyPO = new SecretKeyPO();
            secretKeyPO.setSecretKeyName(JwtConstants.ACCESS_KEY_SECRET_KEY_ENCRYPT_NAME);
            secretKeyPO.setSecretKeyValue(secretValue);
            secretKeyPO.setGmtCreate(new Date());
            secretKeyPO.setGmtModified(new Date());
            secretKeyMapper.insertSelective(secretKeyPO);
        } else {
            ACCESS_KEY_SECRET_KEY_ENCRYPT_VALUE = accessKeySecretKeyPO.getSecretKeyValue();
        }

    }

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
}
