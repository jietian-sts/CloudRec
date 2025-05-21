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
package com.alipay.application.service.account.domain;


import com.alipay.application.service.account.enums.SecurityProductStatus;
import com.alipay.application.service.account.enums.SecurityProductType;
import com.alipay.dao.po.CloudAccountPO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 *@title SecurityProductPosture
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/11 14:53
 */
@Builder
@Getter
@Setter
public class SecurityProductPosture {
    private Long id;
    private CloudAccountPO cloudAccountPO;
    private String productType;
    private String resourceType;
    private String status;
    private String version;
    private String versionDesc;
    private String policy;
    private String policyDetail;
    private Integer protectedCount;
    private Integer total;

    public SecurityProductPosture() {
    }

    public SecurityProductPosture(Long id, CloudAccountPO cloudAccountPO, String productType, String resourceType, String status, String version, String versionDesc, String policy, String policyDetail, Integer protectedCount, Integer total) {
        this.id = id;
        this.cloudAccountPO = cloudAccountPO;
        this.productType = productType;
        this.resourceType = resourceType;
        this.status = status;
        this.version = version;
        this.versionDesc = versionDesc;
        this.policy = policy;
        this.policyDetail = policyDetail;
        this.protectedCount = protectedCount;
        this.total = total;
    }

    public static SecurityProductPosture defaultSecurityProductPosture(CloudAccountPO cloudAccountPO,String productType,String resourceType) {
        SecurityProductPosture securityProductPosture = new SecurityProductPosture();
        securityProductPosture.setCloudAccountPO(cloudAccountPO);
        securityProductPosture.setProductType(productType);
        securityProductPosture.setResourceType(resourceType);
        securityProductPosture.setStatus(SecurityProductStatus.close.name());
        securityProductPosture.setVersion(SecurityProductType.unknown);
        securityProductPosture.setVersionDesc(SecurityProductType.unknown);
        securityProductPosture.setPolicy(SecurityProductType.unknown);
        securityProductPosture.setPolicyDetail(null);
        securityProductPosture.setProtectedCount(0);
        securityProductPosture.setTotal(0);
        return securityProductPosture;
    }
}
