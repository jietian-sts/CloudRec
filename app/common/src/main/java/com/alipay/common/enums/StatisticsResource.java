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
package com.alipay.common.enums;

import java.util.Arrays;
import java.util.List;

/*
 *@title StatisticsResource
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/11 14:16
 */
public enum StatisticsResource {

    ALI_CLOUD("ALI_CLOUD", Arrays.asList("ECS", "SLB", "ALB", "NLB", "RDS", "CDN", "Redis")),
    AWS("AWS", Arrays.asList("EC2", "ELB", "CLB", "ELBv2", "S3")),
    TENCENT_CLOUD("TENCENT_CLOUD",
            Arrays.asList("CVM", "COS", "NAT", "CLB", "CDB", "MariaDB", "PostgreSQL", "SQLServer")),
    HUAWEI_CLOUD("HUAWEI_CLOUD", Arrays.asList("ECS", "VPC", "ELB", "EIP", "OBS", "GaussDB", "CSS", "LTS"));

    private String platform;

    private List<String> showResourceTypeList;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<String> getResourceTypeList() {
        return showResourceTypeList;
    }

    public void setResourceTypeList(List<String> showResourceTypeList) {
        this.showResourceTypeList = showResourceTypeList;
    }

    StatisticsResource(String platform, List<String> showResourceTypeList) {
        this.platform = platform;
        this.showResourceTypeList = showResourceTypeList;
    }

    /**
     * 根据平台获取展示资源类型列表
     *
     * @param platform 平台
     * @return 展示资源类型列表
     */
    public static List<String> getShowResourceListByPlatform(String platform) {
        for (StatisticsResource resource : StatisticsResource.values()) {
            if (resource.getPlatform().equals(platform)) {
                return resource.getResourceTypeList();
            }
        }
        return List.of();
    }
}
