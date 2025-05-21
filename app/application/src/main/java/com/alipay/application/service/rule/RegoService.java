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
package com.alipay.application.service.rule;

import com.alipay.application.share.request.rule.LintRegoRequest;
import com.alipay.application.share.request.rule.QueryRegoListRequest;
import com.alipay.application.share.request.rule.RegoRequest;
import com.alipay.application.share.request.rule.TestRuleRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.rule.RuleRegoVO;
import com.alipay.application.share.vo.rule.TestRegoVO;
import com.alipay.application.service.rule.utils.RegoCmdExecutorUtils;

import java.util.List;
import java.util.Map;

/*
 *@title RegoService
 *@description rego 相关服务接口
 *@author jietian
 *@version 1.0
 *@create 2024/6/6 10:00
 */
public interface RegoService {


    void saveRego(RegoRequest request);


    ApiResponse<ListVO<RuleRegoVO>> queryRegoList(QueryRegoListRequest request);


    ApiResponse<TestRegoVO> testRego(TestRuleRequest testRuleRequest);


    Map<String, Object> executeRego(String rego, String input);


    ApiResponse<RegoCmdExecutorUtils.RegoCmdExecutorResponse> lintRego(LintRegoRequest lintRegoRequest);


    void updateGlobalVariableData(List<Long> globalVariableConfigIdList);
}
