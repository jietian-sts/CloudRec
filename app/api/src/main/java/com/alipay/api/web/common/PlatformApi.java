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
package com.alipay.api.web.common;

import com.alipay.application.service.common.Platform;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.dao.po.PlatformPO;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 *@title PlatformApi
 *@description platform collector
 *@author jietian
 *@version 1.0
 *@create 2024/6/5 18:46
 */
@RestController
@RequestMapping("/api/platform")
@Validated
public class PlatformApi {

    @Resource
    private Platform platform;

    /**
     * Get platform type list interface
     */
    @GetMapping("/platformList")
    public ApiResponse<List<PlatformPO>> queryPlatformList() {
        return new ApiResponse<>(platform.queryPlatformList());
    }
}
