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
package com.alipay.api.web.rule;

import com.alipay.api.config.filter.annotation.aop.AuthenticateToken;
import com.alipay.application.service.rule.RegoService;
import com.alipay.application.service.rule.utils.RegoCmdExecutorUtils;
import com.alipay.application.share.request.rule.LintRegoRequest;
import com.alipay.application.share.request.rule.QueryRegoListRequest;
import com.alipay.application.share.request.rule.RegoRequest;
import com.alipay.application.share.request.rule.TestRuleRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.rule.RuleRegoVO;
import com.alipay.application.share.vo.rule.TestRegoVO;
import com.alipay.common.enums.TestRegoType;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@title RegoController
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/6 09:56
 */
@RestController
@RequestMapping("/api/rego")
@Validated
public class RegoController {

    @Resource
    private RegoService regoService;

    /**
     * Test rego rule interface
     *
     */
    @AuthenticateToken
    @PostMapping("/testRego")
    public ApiResponse<TestRegoVO> testRego(@RequestBody TestRuleRequest testRuleRequest) {
        if (StringUtils.isNotBlank(testRuleRequest.getType())) {
            TestRegoType testRegoType = TestRegoType.getTestRegoType(testRuleRequest.getType());
            if (testRegoType == null) {
                throw new IllegalArgumentException("Unsupported test types");
            }

            if (TestRegoType.tenant.getType().equals(testRuleRequest.getType())) {
                Assert.notNull(testRuleRequest.getSelectId(), "Tenant ID cannot be empty");
            }
            if (TestRegoType.cloud_account.getType().equals(testRuleRequest.getType())) {
                Assert.notNull(testRuleRequest.getSelectId(), "Cloud account id cannot be empty");
            }
        }

        return regoService.testRego(testRuleRequest);
    }

    /**
     * Rule syntax detection
     */
    @PostMapping("/lintRego")
    public ApiResponse<RegoCmdExecutorUtils.RegoCmdExecutorResponse> lintRego(@RequestBody LintRegoRequest lintRegoRequest) {
        return regoService.lintRego(lintRegoRequest);
    }

    /**
     * Query historical version rego information
     */
    @PostMapping("/queryRegoList")
    public ApiResponse<ListVO<RuleRegoVO>> queryRegoList(@RequestBody QueryRegoListRequest request) {
        return regoService.queryRegoList(request);
    }

    /**
     * Save rego rules
     *
     */
    @AuthenticateToken
    @PostMapping("/saveRego")
    public ApiResponse<String> saveRego(@RequestBody RegoRequest req) {
        regoService.saveRego(req);
        return ApiResponse.SUCCESS;
    }
}
