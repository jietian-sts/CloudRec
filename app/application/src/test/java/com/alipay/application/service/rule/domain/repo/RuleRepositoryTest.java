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
package com.alipay.application.service.rule.domain.repo;

import com.alipay.application.service.rule.RuleServiceImpl;
import com.alipay.application.service.rule.domain.RuleAgg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.List;

/*
 *@title RuleRepositoryTest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/18 17:02
 */
@ExtendWith(MockitoExtension.class)
class RuleRepositoryTest {

    @InjectMocks
    private RuleServiceImpl ruleService;

    private RuleRepository ruleRepository;

    @Mock
    private Logger log;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleRepository = new RuleRepositoryImpl();
    }


    @Test
    void findAllOrgRuleList() {
        List<RuleAgg> allOrgRuleList = ruleRepository.findRuleListFromGitHub();
        System.out.println(allOrgRuleList);
    }
}