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
package com.alipay.application.service.system;

import com.alipay.application.share.request.openapi.QueryResourceRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListScrollPageVO;
import com.alipay.application.share.vo.resource.ResourceInstanceVO;
import com.alipay.dao.mapper.CloudResourceInstanceMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

/*
 *@title OpenApiServiceTest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/2 18:44
 */
@RunWith(MockitoJUnitRunner.class)
class OpenApiServiceTest {

    @InjectMocks
    private OpenApiService openApiService;

    @MockBean
    private CloudResourceInstanceMapper cloudResourceInstanceMapper;

    @Test
    void queryResourceList() {
        // 测
        // 模拟mapper的行为
        // 调用测试方法
        QueryResourceRequest queryResourceRequest = new QueryResourceRequest();
        ApiResponse<ListScrollPageVO<ResourceInstanceVO>> listScrollPageVOApiResponse = openApiService.queryResourceList(queryResourceRequest);
        assert listScrollPageVOApiResponse != null;
    }

}