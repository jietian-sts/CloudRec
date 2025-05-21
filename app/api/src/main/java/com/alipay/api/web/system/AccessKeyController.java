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
package com.alipay.api.web.system;


import com.alipay.api.config.filter.annotation.aop.AuthenticateToken;
import com.alipay.application.service.system.AccessKeyService;
import com.alipay.application.share.request.base.IdRequest;
import com.alipay.application.share.request.system.RemarkAccessKeyRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.dao.context.UserInfoContext;
import com.alipay.dao.po.OpenApiAuthPO;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/*
 *@title MyCenterController
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/3 17:14
 */
@RestController
@RequestMapping("/api/accessKey")
public class AccessKeyController {

    @Resource
    private AccessKeyService accessKeyService;

    @AuthenticateToken
    @PostMapping("/createAccessKey")
    public ApiResponse<String> createAccessKey() throws NoSuchAlgorithmException {
        accessKeyService.createAccessKey();
        return ApiResponse.SUCCESS;
    }

    @AuthenticateToken
    @DeleteMapping("/deleteAccessKey")
    public ApiResponse<String> deleteAccessKey(@RequestBody @Validated IdRequest idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ApiResponse<>(bindingResult);
        }
        accessKeyService.deleteAccessKey(idRequest.getId());
        return ApiResponse.SUCCESS;
    }

    @AuthenticateToken
    @PostMapping("/remarkAccessKey")
    public ApiResponse<String> remarkAccessKey(@RequestBody @Validated RemarkAccessKeyRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ApiResponse<>(bindingResult);
        }
        accessKeyService.remarkAccessKey(request.getId(), request.getRemark());
        return ApiResponse.SUCCESS;
    }

    @AuthenticateToken
    @GetMapping("/queryAccessKeyList")
    public ApiResponse<List<OpenApiAuthPO>> queryAccessKeyList() {
        List<OpenApiAuthPO> list = accessKeyService.queryAccessKeyList(UserInfoContext.getCurrentUser().getUserId());
        return new ApiResponse<>(list);
    }
}
