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
package com.alipay.application.share.vo.resource;

import com.alipay.application.service.resource.identitySecurity.model.ResourceAccessInfoDTO;
import com.alipay.application.service.resource.identitySecurity.model.ResourcePolicyInfoDTO;
import com.alipay.application.service.resource.identitySecurity.model.ResourceUserInfoDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Date: 2025/4/25
 * Author: lz
 */
@Data
public class IdentitySecurityVO {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String resourceId;

    private List<String> tags;

    private String accessType;

    private String resourceType;

    private List<ResourceAccessInfoDTO> accessInfos;

    private ResourceUserInfoDTO userInfo;

    private List<ResourcePolicyInfoDTO> policies;

    private String activityLogs;

    private String instance;

    private String platform;

    private String cloudAccountId;

    private String ruleIds;

    private String resourceName;

    private String resourceTypeGroup;

}
