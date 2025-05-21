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
/*
 *@title RuleGroupService
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/5 19:00
 */

import com.alipay.application.share.request.rule.RuleGroupRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.rule.RuleGroupVO;
import com.alipay.dao.dto.RuleGroupDTO;

import java.util.List;

public interface RuleGroupService {

    ApiResponse<String> deleteRuleGroup(Long id);

    ApiResponse<ListVO<RuleGroupVO>> queryRuleGroupList(RuleGroupRequest request);

    ApiResponse<String> saveRuleGroup(RuleGroupDTO dto);

    List<String> queryRuleGroupNameList();

    ApiResponse<RuleGroupVO> queryRuleGroupDetail(Long id);
}
