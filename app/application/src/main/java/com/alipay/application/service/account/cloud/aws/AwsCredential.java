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
package com.alipay.application.service.account.cloud.aws;

import com.alipay.application.service.account.cloud.Credential;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeRegionsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

import java.util.List;

/*
 *@title AliCloudCredential
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/19 11:59
 */
public class AwsCredential implements Credential {

    private final String ak;
    private final String sk;

    public String getAk() {
        return ak;
    }

    public String getSk() {
        return sk;
    }

    public AwsCredential(String ak, String sk) {
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
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ak, sk);
        try (Ec2Client ec2 = Ec2Client.builder().region(software.amazon.awssdk.regions.Region.AP_SOUTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build()) {

            DescribeRegionsResponse describeRegionsResponse = ec2.describeRegions();

            return describeRegionsResponse.regions().stream().map(r -> {
                Region region = new Region();
                region.setRegionId(r.regionName());
                region.setName(r.regionName());
                return region;
            }).toList();
        } catch (Ec2Exception e) {
            try (Ec2Client ec2 = Ec2Client.builder().region(software.amazon.awssdk.regions.Region.CN_NORTH_1)
                    .credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build()) {

                DescribeRegionsResponse describeRegionsResponse = ec2.describeRegions();

                return describeRegionsResponse.regions().stream().map(r -> {
                    Region region = new Region();
                    region.setRegionId(r.regionName());
                    region.setName(r.regionName());
                    return region;
                }).toList();
            } catch (Ec2Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }

    }
}
