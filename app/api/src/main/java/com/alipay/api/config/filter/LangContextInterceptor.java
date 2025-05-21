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
package com.alipay.api.config.filter;


import com.alipay.api.config.MessageConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.Locale;

/*
 *@title LangContextInterceptor
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/4 11:37
 */
@Component
public class LangContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String language = null;
        Locale locale= Locale.getDefault();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (MessageConfig.lang.equalsIgnoreCase(headerName)) {
                language = request.getHeader(headerName);
                locale = Locale.forLanguageTag(language);
                break;
            }
        }

        LocaleContextHolder.setLocale(locale);
        return true;
    }
}
