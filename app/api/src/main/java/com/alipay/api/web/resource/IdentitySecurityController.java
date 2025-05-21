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
package com.alipay.api.web.resource;

import com.alipay.api.config.filter.annotation.aop.AuthenticateToken;
import com.alipay.application.service.resource.identitySecurity.IdentitySecurityService;
import com.alipay.application.share.request.resource.QueryIdentityRuleRequest;
import com.alipay.application.share.request.resource.QueryIdentityCardRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.resource.IdentityCardVO;
import com.alipay.application.share.vo.resource.IdentitySecurityRiskInfoVO;
import com.alipay.application.share.vo.resource.IdentitySecurityVO;
import com.alipay.dao.po.PlatformPO;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Date: 2025/4/16
 * Author: lz
 */
@RestController
@RequestMapping("/api/identity")
@Validated
public class IdentitySecurityController {

    @Resource
    private IdentitySecurityService identitySecurityService;

    @PostMapping("/groupTagList")
    public ApiResponse<List<String>> groupTagList(){
        return new ApiResponse<>(identitySecurityService.getTagList());
    }
    /**
     * 查询身份模块信息
     * @param request
     * @return
     */
    @PostMapping("/queryIdentityList")
    public ApiResponse<ListVO<IdentitySecurityVO>> queryIdentityList(@RequestBody QueryIdentityRuleRequest request) {
        ListVO<IdentitySecurityVO> identitySecurityListVO = identitySecurityService.queryIdentitySecurityList(request);
        return new ApiResponse<>(identitySecurityListVO);
    }

    @PostMapping("/queryIdentity/{id}")
    public ApiResponse<IdentitySecurityVO> queryIdentity(@PathVariable Long id) {
        return new ApiResponse<>(identitySecurityService.queryIdentitySecurityDetail(id));
    }

    @PostMapping("/queryRiskInfo")
    public ApiResponse<List<IdentitySecurityRiskInfoVO>> queryRiskInfo(@RequestBody QueryIdentityRuleRequest request) {
        List<IdentitySecurityRiskInfoVO> identitySecurityRiskInfoVOS = identitySecurityService.queryRiskInfo(request);
        return new ApiResponse<>(identitySecurityRiskInfoVOS);
    }

    @GetMapping("/getPlatformList")
    public ApiResponse<List<PlatformPO>> queryPlatformList() {
        return new ApiResponse<>(identitySecurityService.getPlatformList());
    }


    /**
     * 查询身份模块卡片
     * @param request
     * @return
     */
    @AuthenticateToken
    @PostMapping("/queryIdentityCardList")
    public ApiResponse<List<IdentityCardVO>> queryIdentityCardList(@RequestBody QueryIdentityCardRequest request) {
        return new ApiResponse<>(identitySecurityService.queryIdentityCardListWithRulds(request));
    }

}
