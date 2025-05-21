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
package com.alipay.api.collector;

import com.alipay.application.service.collector.AgentService;
import com.alipay.application.service.resource.SaveResourceService;
import com.alipay.application.share.request.collector.*;
import com.alipay.application.share.request.resource.DataPushRequest;
import com.alipay.application.share.vo.collector.AgentCloudAccountVO;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.collector.Registry;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 *@title AgentApi
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/7 16:02
 */
@RestController
@RequestMapping("/api/agent")
@Slf4j
public class CollectorApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorApi.class);

    @Resource
    private SaveResourceService saveResourceService;

    @Resource
    private AgentService agentService;

    @PostMapping("/resource")
    public ApiResponse<String> acceptResourceData(@Validated @RequestBody DataPushRequest dataPushRequest,
                                                  BindingResult err) throws InterruptedException {
        if (err.hasErrors()) {
            return new ApiResponse<>(err);
        }

        saveResourceService.acceptResourceData(dataPushRequest);
        return ApiResponse.SUCCESS;
    }

    @PostMapping("/acceptRunningFinishSignal")
    public void acceptRunningFinishSignal(@Validated @RequestBody RunningFinishSignalRequest req,
                                          BindingResult err) {
        if (err.hasErrors()) {
            throw new IllegalArgumentException(err.toString());
        }

        agentService.runningFinishSignal(req.getCloudAccountId());
    }


    /**
     * Read account information interface
     *
     * @param request HTTP request object
     * @param req     Query account account request object
     * @return Return to account account list
     */
    @PostMapping("/listCloudAccount")
    public ApiResponse<List<AgentCloudAccountVO>> listCloudAccount(HttpServletRequest request,
                                                                        @RequestBody QueryCloudAccountRequest req) {
        return agentService.queryCloudAccountList(request.getHeader("PERSISTENTTOKEN"),
                req.getRegistryValue(), req.getPlatform(), req.getSites());
    }

    /**
     * Agent registration, verification of temporary tokens, and generation of persistent tokens
     *
     * @param request  HTTP request object
     * @param registry Register information object
     * @return Return registration response result
     */
    @PostMapping("/registry")
    public ApiResponse<Registry.RegistryResponse> registry(HttpServletRequest request, @RequestBody Registry registry) {
        String onceToken = request.getHeader("ONCETOKEN");
        if (StringUtils.isEmpty(onceToken)) {
            throw new RuntimeException("ONCE_TOKEN is empty");
        }
        agentService.checkOnceToken(registry, onceToken);
        return agentService.registry(registry, onceToken);
    }

    /**
     * Update service type, update account platform information
     *
     * @param request Register information object
     * @return resp info
     */
    @PostMapping("/acceptSupportResourceType")
    public ApiResponse<String> acceptSupportResourceType(HttpServletRequest request,
                                                         @RequestBody AcceptSupportResourceTypeRequest acceptSupportResourceTypeRequest) {
        String persistentToken = request.getHeader("PERSISTENTTOKEN");
        if (StringUtils.isEmpty(persistentToken)) {
            throw new RuntimeException("PERSISTENT_TOKEN is empty");
        }
        agentService.checkPersistentToken(acceptSupportResourceTypeRequest.getPlatform(),
                acceptSupportResourceTypeRequest.getRegistryValue(), persistentToken);
        agentService.acceptSupportResourceType(acceptSupportResourceTypeRequest);
        return ApiResponse.SUCCESS;
    }

    @PostMapping("/log-endpoint")
    public ApiResponse<String> log(@RequestBody LogRequest logRequest) {
        agentService.log(logRequest);
        return ApiResponse.SUCCESS;
    }
}
