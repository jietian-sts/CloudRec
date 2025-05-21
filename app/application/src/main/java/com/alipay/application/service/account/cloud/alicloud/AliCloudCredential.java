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

import com.alipay.application.service.account.cloud.Credential;
import com.aliyun.ecs20140526.Client;
import com.aliyun.ecs20140526.models.DescribeRegionsRequest;
import com.aliyun.ecs20140526.models.DescribeRegionsResponse;
import com.aliyun.ecs20140526.models.DescribeRegionsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/*
 *@title AliCloudCredential
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/19 11:59
 */
@Getter
@Slf4j
public class AliCloudCredential implements Credential {

    private final String ak;
    private final String sk;


    public AliCloudCredential(Map<String, String> accountCredentialsInfo) {
        this.ak = accountCredentialsInfo.get("ak");
        this.sk = accountCredentialsInfo.get("sk");
    }

    @Override
    public boolean verification() {
        try {
            regions();
        } catch (Exception e) {
            log.error("ak or sk is invalid", e);
            return false;
        }
        return true;
    }

    @Override
    public List<Region> regions() {
        try {
            Config config = new Config().setAccessKeyId(ak).setAccessKeySecret(sk).setEndpoint("ecs.aliyuncs.com");
            Client client = new Client(config);
            DescribeRegionsRequest describeRegionsRequest = new DescribeRegionsRequest();
            DescribeRegionsResponse describeRegionsResponse = client.describeRegions(describeRegionsRequest);
            List<DescribeRegionsResponseBody.DescribeRegionsResponseBodyRegionsRegion> regions = describeRegionsResponse
                    .getBody().getRegions().getRegion();
            return regions.stream().map(region -> {
                Region result = new Region();
                result.setRegionId(region.regionId);
                result.setName(region.localName);
                result.setEndpoint(region.getRegionEndpoint());
                return result;
            }).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
