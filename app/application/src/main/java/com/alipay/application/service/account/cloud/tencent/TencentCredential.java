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
package com.alipay.application.service.account.cloud.tencent;

import com.alipay.application.service.account.cloud.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import com.tencentcloudapi.cvm.v20170312.models.DescribeRegionsRequest;
import com.tencentcloudapi.cvm.v20170312.models.DescribeRegionsResponse;

import java.util.Arrays;
import java.util.List;

/*
 *@title TencentCredential
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/19 13:39
 */
public class TencentCredential implements Credential {

    private final String ak;

    private final String sk;

    public String getAk() {
        return ak;
    }

    public String getSk() {
        return sk;
    }

    public TencentCredential(String ak, String sk) {
        this.ak = ak;
        this.sk = sk;
    }

    @Override
    public boolean verification() {
        try {
            regions();
        } catch (Exception e) {
            throw new RuntimeException("Cloud account verification failed:" + e.getMessage());
        }
        return true;
    }

    @Override
    public List<Region> regions() {
        try {
            com.tencentcloudapi.common.Credential credential = new com.tencentcloudapi.common.Credential(ak, sk);
            CvmClient client = new CvmClient(credential, "");
            DescribeRegionsRequest req = new DescribeRegionsRequest();
            DescribeRegionsResponse resp = client.DescribeRegions(req);
            return Arrays.stream(resp.getRegionSet()).toList().stream().map(r -> {
                Region region = new Region();
                region.setRegionId(r.getRegion());
                region.setName(r.getRegionName());
                return region;
            }).toList();
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
