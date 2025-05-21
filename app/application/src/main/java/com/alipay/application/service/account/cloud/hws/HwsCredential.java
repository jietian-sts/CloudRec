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
package com.alipay.application.service.account.cloud.hws;

import com.alipay.application.service.account.cloud.Credential;
import com.huaweicloud.sdk.core.auth.GlobalCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.iam.v3.IamClient;
import com.huaweicloud.sdk.iam.v3.model.KeystoneListRegionsRequest;
import com.huaweicloud.sdk.iam.v3.model.KeystoneListRegionsResponse;
import com.huaweicloud.sdk.iam.v3.region.IamRegion;

import java.util.List;

/*
 *@title HwsCredential
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/19 13:39
 */
public class HwsCredential implements Credential {
    private final String ak;
    private final String sk;

    public String getAk() {
        return ak;
    }

    public String getSk() {
        return sk;
    }

    public HwsCredential(String ak, String sk) {
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
            ICredential auth = new GlobalCredentials().withAk(ak).withSk(sk);
            IamClient client = IamClient.newBuilder().withCredential(auth).withRegion(IamRegion.valueOf("cn-north-1"))
                    .build();
            KeystoneListRegionsRequest request = new KeystoneListRegionsRequest();
            KeystoneListRegionsResponse keystoneListRegionsResponse = client.keystoneListRegions(request);
            List<com.huaweicloud.sdk.iam.v3.model.Region> regions = keystoneListRegionsResponse.getRegions();
            return regions.stream().map(r -> {
                Region region = new Region();
                region.setRegionId(r.getId());
                region.setName(r.getLocales().getZhCn());
                return region;
            }).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
