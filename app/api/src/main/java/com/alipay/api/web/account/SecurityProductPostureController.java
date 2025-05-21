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
package com.alipay.api.web.account;


import com.alipay.api.config.filter.annotation.aop.AuthenticateToken;
import com.alipay.application.service.account.SecurityProductPostureService;
import com.alipay.application.share.request.account.GetCloudAccountSecurityProductPostureListRequest;
import com.alipay.application.share.request.account.OpenSecurityProductRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.account.CloudAccountSecurityProductPostureVO;
import com.alipay.application.share.vo.account.SecurityProductOverallPostureVO;
import com.alipay.common.exception.BizException;
import com.alipay.dao.po.PlatformPO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *@title SecurityProductPostureController
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 10:51
 */
@Slf4j
@RestController
@RequestMapping("/api/cloudAccount/securityProduct")
public class SecurityProductPostureController {

    @Resource
    private SecurityProductPostureService securityProductPostureService;


    /**
     * 获取支持安全管控的云平台列表
     *
     * @return {@link ApiResponse }<{@link ListVO }>
     */
    @AuthenticateToken
    @GetMapping("/getPlatformList")
    public ApiResponse<List<PlatformPO>> getPlatformList() {
        return new ApiResponse<>(securityProductPostureService.getPlatformList());
    }


    /**
     * 分租户 根据云平台获取安全产品总体态势
     *
     * @return {@link ApiResponse }<{@link SecurityProductOverallPostureVO }>
     */
    @AuthenticateToken
    @GetMapping("/getOverallPosture")
    public ApiResponse<SecurityProductOverallPostureVO> getOverallPosture(@RequestParam String platform) {
        SecurityProductOverallPostureVO vo = securityProductPostureService.getOverallPosture(platform);
        return new ApiResponse<>(vo);
    }


    /**
     * 查询云账号列表以及云账号下云安全的开通情况
     */
    @AuthenticateToken
    @PostMapping("/getCloudAccountSecurityProductPostureList")
    public ApiResponse<ListVO<CloudAccountSecurityProductPostureVO>> getCloudAccountSecurityProductPostureList(@RequestBody GetCloudAccountSecurityProductPostureListRequest request) {
        try {
            ListVO<CloudAccountSecurityProductPostureVO> list = securityProductPostureService.getCloudAccountSecurityProductPostureList(request);
            return new ApiResponse<>(list);
        } catch (Exception e) {
            log.error("getCloudAccountSecurityProductPostureList field", e);
            throw new BizException(e.getMessage());
        }
    }


    /**
     * 查询云账号列表以及云账号下云安全的开通情况
     */
    @AuthenticateToken
    @PostMapping("/openSecurityProduct")
    public ApiResponse<String> openSecurityProduct(@Valid @RequestBody OpenSecurityProductRequest request) {
        return new ApiResponse<>(securityProductPostureService.openSecurityProduct(request.getPlatform(), request.getCloudAccountId(), request.getProductType()));
    }

}
