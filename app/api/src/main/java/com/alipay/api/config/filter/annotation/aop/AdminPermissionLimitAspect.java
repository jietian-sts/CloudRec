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

import com.alipay.application.service.system.domain.User;
import com.alipay.application.service.system.domain.enums.RoleNameType;
import com.alipay.application.service.system.utils.TokenUtil;
import com.alipay.common.exception.UserNoLoginException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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
public class AdminPermissionLimitAspect {

    @Before("@annotation(adminPermissionLimit)")
    public void auth(AdminPermissionLimit adminPermissionLimit) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader(adminPermissionLimit.value());
        if (StringUtils.isBlank(token)) {
            throw new UserNoLoginException("Token is blank");
        }

        User user = TokenUtil.parseToken(token);
        if (user == null) {
            throw new UserNoLoginException("Parse token error");
        }

        if (!RoleNameType.admin.name().equals(user.getRoleName().name())) {
            throw new UserNoLoginException("Permission denied,Requires administrator rights");
        }
    }
}
