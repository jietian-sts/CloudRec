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
package com.alipay.application.service.account.cloud.alicloud;

import com.alipay.application.service.resource.IQueryResource;
import com.alipay.dao.dto.IQueryResourceDTO;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.mapper.SecurityProductPostureMapper;
import com.alipay.dao.po.CloudAccountPO;
import com.alipay.dao.po.CloudResourceInstancePO;
import com.alipay.dao.po.SecurityProductPosturePO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 *@title AliCloudDataProducerTest
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 17:51
 */
public class AliCloudDataProducerTest {


    @Mock
    private IQueryResource iQueryResource;

    @Mock
    private CloudAccountMapper cloudAccountMapper;

    @Mock
    private SecurityProductPostureMapper securityProductPostureMapper;

    @InjectMocks
    private AliCloudDataProducer aliCloudDataProducer;

    private CloudAccountPO cloudAccountPO;

    private CloudResourceInstancePO cloudResourceInstancePO;

    private SecurityProductPosturePO securityProductPosturePO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cloudAccountPO = new CloudAccountPO();
        cloudAccountPO.setCloudAccountId("1234567890");
        cloudAccountPO.setPlatform("ALI_CLOUD");

        cloudResourceInstancePO = new CloudResourceInstancePO();
        cloudResourceInstancePO.setCloudAccountId("1234567890");
        cloudResourceInstancePO.setPlatform("ALI_CLOUD");
        cloudResourceInstancePO.setResourceType("SAS");
        cloudResourceInstancePO.setResourceId("1234567890");
        cloudResourceInstancePO.setInstance("{}");

        securityProductPosturePO = new SecurityProductPosturePO();
        securityProductPosturePO.setCloudAccountId("1234567890");
        securityProductPosturePO.setPlatform("ALI_CLOUD");
        securityProductPosturePO.setResourceType("SAS");
        securityProductPosturePO.setCloudAccountId("1234567890");
    }


    /**
     * [单测用例]测试场景：SAS产品存在
     */
    @Test
    public void testProductSecurityProductStatisticsData_SASExists() {

        when(cloudAccountMapper.findList(any())).thenReturn(Collections.singletonList(cloudAccountPO));
        when(aliCloudDataProducer.getCloudAccountList(any())).thenReturn(Collections.singletonList(cloudAccountPO));
        when(iQueryResource.queryResource(any(IQueryResourceDTO.class))).thenReturn(cloudResourceInstancePO);
        when(securityProductPostureMapper.findOne(any(), any(), any())).thenReturn(null);

        aliCloudDataProducer.productSecurityProductStatisticsData();
    }
}