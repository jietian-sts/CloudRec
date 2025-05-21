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

import com.alipay.api.config.filter.annotation.aop.AdminPermissionLimit;
import com.alipay.api.config.filter.annotation.aop.AuthenticateToken;
import com.alipay.api.web.system.request.QueryTenantListRequest;
import com.alipay.application.service.system.TenantService;
import com.alipay.application.service.system.domain.Tenant;
import com.alipay.application.service.system.domain.enums.Status;
import com.alipay.application.share.request.admin.ChangeTenantRequest;
import com.alipay.application.share.request.admin.JoinUserRequest;
import com.alipay.application.share.request.admin.QueryMemberRequest;
import com.alipay.application.share.request.admin.SaveTenantRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.system.TenantVO;
import com.alipay.application.share.vo.system.UserVO;
import com.alipay.dao.context.UserInfoContext;
import com.alipay.dao.context.UserInfoDTO;
import com.alipay.dao.dto.TenantDTO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *@title TenantApi
 *@description Tenant-related interfaces
 *@author jietian
 *@version 1.0
 *@create 2024/6/13 17:41
 */
@RestController
@RequestMapping("/api/tenant")
@Validated
public class TenantController {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TenantController.class);

    @Resource
    private TenantService tenantService;

    /**
     * Get a list of tenants
     */
    @PostMapping("/queryTenantList")
    public ApiResponse<ListVO<TenantVO>> findList(@Validated @RequestBody QueryTenantListRequest request,
                                                  BindingResult error) {
        if (error.hasErrors()) {
            return new ApiResponse<>(error);
        }

        TenantDTO tenantDTO = new TenantDTO();
        BeanUtils.copyProperties(request, tenantDTO);
        ListVO<TenantVO> listVO = tenantService.findList(tenantDTO);
        return new ApiResponse<>(listVO);
    }

    @GetMapping("/queryAllTenantList")
    public ApiResponse<ListVO<TenantVO>> queryAllTenantList() {
        ListVO<TenantVO> listVO = tenantService.findAll();
        return new ApiResponse<>(listVO);
    }

    /**
     * Save tenant information
     */
    @AdminPermissionLimit
    @PostMapping("/saveTenant")
    public ApiResponse<String> saveTenant(@Validated @RequestBody SaveTenantRequest req,
                                          BindingResult error) {
        if (error.hasErrors()) {
            return new ApiResponse<>(error);
        }

        Tenant tenant = new Tenant(req.getId(), req.getTenantName(), Status.getStatus(req.getStatus()), req.getTenantDesc());

        tenantService.saveTenant(tenant);

        return ApiResponse.SUCCESS;
    }

    /**
     * View members within tenant
     */
    @PostMapping("/queryMember")
    public ApiResponse<ListVO<UserVO>> queryMember(@Validated @RequestBody QueryMemberRequest request,
                                                   BindingResult error) {
        if (error.hasErrors()) {
            return new ApiResponse<>(error);
        }

        TenantDTO tenantDTO = new TenantDTO();
        BeanUtils.copyProperties(request, tenantDTO);

        ListVO<UserVO> listVO = tenantService.queryMemberList(tenantDTO);
        return new ApiResponse<>(listVO);
    }

    /**
     * Add a member
     *
     * @param request request
     */
    @AdminPermissionLimit
    @PostMapping("/joinUser")
    public ApiResponse<String> joinUser(@Validated @RequestBody JoinUserRequest request, BindingResult error) {
        if (error.hasErrors()) {
            return new ApiResponse<>(error);
        }

        tenantService.joinUser(request.getUserId(), request.getTenantId());
        return ApiResponse.SUCCESS;
    }

    /**
     * Remove members
     *
     * @param uid      User table primary key ID
     * @param tenantId Tenant table primary key ID
     */
    @AdminPermissionLimit
    @DeleteMapping("/removeUser")
    public ApiResponse<String> removeUser(@RequestParam(name = "userId") Long uid, @RequestParam Long tenantId) {
        return tenantService.removeUser(uid, tenantId);
    }

    /**
     * Switch the current user's tenant
     */
    @PostMapping("/changeTenant")
    @AuthenticateToken
    public ApiResponse<String> changeTenant(@Validated @RequestBody ChangeTenantRequest changeTenantRequest, BindingResult error) {
        if (error.hasErrors()) {
            return new ApiResponse<>(error);
        }
        return tenantService.changeTenant(UserInfoContext.getCurrentUser().getUserId(), changeTenantRequest.getTenantId());
    }

    /**
     * Query the list of tenants that the current user has joined
     */
    @GetMapping("/listAddedTenants")
    @AuthenticateToken
    public ApiResponse<List<TenantVO>> listAddedTenants() {
        UserInfoDTO currentUser = UserInfoContext.getCurrentUser();
        return tenantService.listAddedTenants(currentUser.getUserId());
    }

}
