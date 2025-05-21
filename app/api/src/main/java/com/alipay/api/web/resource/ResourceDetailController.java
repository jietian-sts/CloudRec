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
import com.alipay.application.service.resource.ResourceDetailConfigService;
import com.alipay.application.share.request.resource.QueryDetailConfigListRequest;
import com.alipay.application.share.request.resource.SaveDetailConfigRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.resource.ResourceDetailConfigVO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/*
 *@title ResourceController
 *@description Asset details configuration related interfaces
 *@author jietian
 *@version 1.0
 *@create 2024/6/6 17:13
 */
@RestController
@RequestMapping("/api/resourceDetailConfig")
@Validated
public class ResourceDetailController {

    @Resource
    private ResourceDetailConfigService resourceDetailConfigService;

    @AuthenticateToken
    @PostMapping("/saveDetailConfig")
    public ApiResponse<String> saveDetailConfig(@Validated @RequestBody Map<String, List<SaveDetailConfigRequest>> request) {
        return resourceDetailConfigService.saveDetailConfig(request);
    }

    /**
     * Query asset details configuration list (no paging required)
     */
    @PostMapping("/queryDetailConfigList")
    public ApiResponse<Map<String, List<ResourceDetailConfigVO>>> queryDetailConfigList(
            @Validated @RequestBody QueryDetailConfigListRequest request) {

        request.setResourceIdEq(request.getResourceId());
        return resourceDetailConfigService.queryDetailConfigList(request, null);
    }
}
