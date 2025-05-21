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
package com.alipay.application.service.account;


import com.alipay.application.share.request.account.GetCloudAccountSecurityProductPostureListRequest;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.account.CloudAccountSecurityProductPostureVO;
import com.alipay.application.share.vo.account.SecurityProductOverallPostureVO;
import com.alipay.dao.po.PlatformPO;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/*
 *@title SecurityProductPostureService
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 10:55
 */
public interface SecurityProductPostureService {

    /**
     * 获取安全产品整体态势
     *
     * @param platform 平台
     * @return 安全产品整体态势
     */
    SecurityProductOverallPostureVO getOverallPosture(String platform);

    /**
     * 获取云账号安全产品态势列表
     *
     * @param request req
     * @return 云账号安全产品态势列表
     */
    ListVO<CloudAccountSecurityProductPostureVO> getCloudAccountSecurityProductPostureList(GetCloudAccountSecurityProductPostureListRequest request);

    /**
     * 开启云产品防护
     *
     * @param platform       平台
     * @param cloudAccountId 云账号id
     * @param productType    产品类型
     * @return 开通情况
     */
    String openSecurityProduct(@NotEmpty String platform, @NotEmpty String cloudAccountId, @NotEmpty String productType);

    /**
     * 获取支持安全管控的云平台列表
     *
     * @return 支持安全管控的云平台列表
     */
    List<PlatformPO> getPlatformList();
}
