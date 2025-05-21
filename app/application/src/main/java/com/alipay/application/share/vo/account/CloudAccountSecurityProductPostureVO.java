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
package com.alipay.application.share.vo.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/*
 *@title CloudAccountSecurityProductPosture
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 11:48
 */
@Getter
@Setter
public class CloudAccountSecurityProductPostureVO {

    /**
     * 云平台
     */
    private String platform;

    /**
     * 云账号id
     */
    private String cloudAccountId;

    /**
     * 云账号别名
     */
    private String alias;

    /**
     * 资产数量
     */
    private Integer total;

    /**
     * 归属租户
     */
    private String tenantName;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 安全产品开启情况
     */
    private List<ProductPosture> productPostureList;

    @Getter
    @Setter
    public static class ProductPosture {
        private String productType;
        private String version;
        private String policy;
        private String policyDetail;
        private String status;
        private Integer protectedCount;
        private Integer total;

        public ProductPosture(String productType, String version, String policy, String policyDetail, String status, Integer protectedCount, Integer total) {
            this.productType = productType;
            this.version = version;
            this.policy = policy;
            this.status = status;
            this.policyDetail = policyDetail;
            this.protectedCount = protectedCount;
            this.total = total;
        }
    }
}
