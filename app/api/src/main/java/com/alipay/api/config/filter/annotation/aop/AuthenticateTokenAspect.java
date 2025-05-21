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
package com.alipay.api.config.filter.annotation.aop;

import com.alipay.application.service.system.domain.Tenant;
import com.alipay.application.service.system.domain.User;
import com.alipay.application.service.system.domain.repo.TenantRepository;
import com.alipay.application.service.system.domain.repo.UserRepository;
import com.alipay.application.service.system.utils.TokenUtil;
import com.alipay.common.constant.TenantConstants;
import com.alipay.common.exception.UserNoLoginException;
import com.alipay.dao.context.UserInfoContext;
import com.alipay.dao.context.UserInfoDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*
 *@title TokenParserAspect
 *@description Used to parse tokens and determine the current tenant permissions
 *@author jietian
 *@version 1.0
 *@create 2024/6/17 15:04
 */
@Aspect
@Component
public class AuthenticateTokenAspect {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TenantRepository tenantRepository;


    @Before("@annotation(authenticateToken)")
    public void authenticate(AuthenticateToken authenticateToken) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String token = request.getHeader(authenticateToken.value());
            if (StringUtils.isBlank(token)) {
                throw new UserNoLoginException("Token is blank");
            }
            User user = TokenUtil.parseToken(token);
            if (user == null) {
                throw new UserNoLoginException("Certificate expired, please login again");
            }

            user = userRepository.find(user.getUserId());
            if (user == null) {
                throw new UserNoLoginException("User not exist");
            }

            if (user.getTenantId() == null) {
                throw new UserNoLoginException("No tenant selected yet");
            }

            Tenant tenant = tenantRepository.find(user.getTenantId());
            if (tenant == null) {
                throw new UserNoLoginException("The currently selected tenant no longer exists");
            }

            UserInfoDTO userInfoDTO = buildUserInfo(tenant, user);

            UserInfoContext.setCurrentUser(userInfoDTO);
        } catch (Exception e) {
            UserInfoContext.clear();
            throw new UserNoLoginException("some thing went wrong");
        }
    }

    @After("@annotation(com.alipay.api.config.filter.annotation.aop.AuthenticateToken)")
    public void clearUserInfo() {
        UserInfoContext.clear();
    }


    private static UserInfoDTO buildUserInfo(Tenant tenant, User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        if (!TenantConstants.GLOBAL_TENANT.equals(tenant.getTenantName())) {
            userInfoDTO.setTenantId(user.getTenantId());
        } else {
            userInfoDTO.setGlobalTenantId(user.getTenantId());
        }

        userInfoDTO.setUid(user.getId());
        userInfoDTO.setTenantName(tenant.getTenantName());
        userInfoDTO.setUserId(user.getUserId());
        userInfoDTO.setUsername(user.getUsername());
        return userInfoDTO;
    }
}
