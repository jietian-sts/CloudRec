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

import com.alipay.api.config.filter.annotation.aop.AuthenticateToken;
import com.alipay.application.service.common.SystemConfigService;
import com.alipay.application.share.request.common.SystemConfigRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.dao.po.SystemConfigPO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * Date: 2025/4/28
 * Author: lz
 */
@RestController
@RequestMapping("/api/systemConfig")
public class SystemConfigController {

    @Resource
    private SystemConfigService systemConfigService;

    @AuthenticateToken
    @PostMapping("/save")
    public ApiResponse<SystemConfigPO> querySystemConfig(@RequestBody SystemConfigRequest request) {
        return new ApiResponse<>(systemConfigService.save(request));
    }

    @AuthenticateToken
    @PostMapping("/delete/{id}")
    public ApiResponse<Integer> delete(@PathVariable Long id) {
        return new ApiResponse<>(systemConfigService.deleteById(id));
    }
}
