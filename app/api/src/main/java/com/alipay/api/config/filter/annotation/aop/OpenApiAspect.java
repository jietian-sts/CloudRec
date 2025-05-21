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

import com.alipay.application.service.system.utils.DigestSignUtils;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.common.exception.OpenAipNoAuthException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class OpenApiAspect {

    @Resource
    private DigestSignUtils digestSignUtils;

    @Pointcut("@annotation(com.alipay.api.config.filter.annotation.aop.OpenApi)")
    public void annotatedMethod() {
    }

    @Before("@annotation(com.alipay.api.config.filter.annotation.aop.OpenApi)")
    public void processRequest(JoinPoint joinPoint) {
        HttpServletRequest request = getRequestFromArgs(joinPoint.getArgs());
        ApiResponse<String> response = digestSignUtils.isAuth(request);
        if (response.getCode() != ApiResponse.SUCCESS_CODE) {
            throw new OpenAipNoAuthException(response.getMsg());
        }


    }

    private HttpServletRequest getRequestFromArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                return (HttpServletRequest) arg;
            }
        }
        return null;
    }



}
