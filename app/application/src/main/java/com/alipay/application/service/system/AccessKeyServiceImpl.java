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
package com.alipay.application.service.system;


import com.alipay.application.service.system.utils.DigestSignUtils;
import com.alipay.dao.context.UserInfoContext;
import com.alipay.dao.context.UserInfoDTO;
import com.alipay.dao.mapper.OpenApiAuthMapper;
import com.alipay.dao.po.OpenApiAuthPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/*
 *@title AccessKeyServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/3 17:19
 */
@Service
public class AccessKeyServiceImpl implements AccessKeyService {

    @Resource
    private OpenApiAuthMapper openApiAuthMapper;

    /**
     * The maximum number of aks that users can create
     */
    private static final int MAX_ACCESS_KEY_COUNT = 3;

    @Override
    public void createAccessKey() throws NoSuchAlgorithmException {
        UserInfoDTO currentUser = UserInfoContext.getCurrentUser();
        List<OpenApiAuthPO> openApiAuthPOS = this.queryAccessKeyList(currentUser.getUserId());
        if (openApiAuthPOS.size() >= MAX_ACCESS_KEY_COUNT) {
            throw new RuntimeException("Only 3 AccessKeys can be created at most");
        }

        OpenApiAuthPO openApiAuthPO = new OpenApiAuthPO();
        openApiAuthPO.setUserId(currentUser.getUserId());
        String accessKey = UUID.randomUUID().toString();
        String secretKey = DigestSignUtils.generateKey(accessKey);
        openApiAuthPO.setAccessKey(accessKey);
        openApiAuthPO.setSecretKey(secretKey);
        openApiAuthMapper.insertSelective(openApiAuthPO);
    }

    @Override
    public void deleteAccessKey(Long id) {
        openApiAuthMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void remarkAccessKey(Long id, String remark) {
        OpenApiAuthPO openApiAuthPO = openApiAuthMapper.selectByPrimaryKey(id);
        if (openApiAuthPO == null) {
            throw new RuntimeException("accessKey does not exist");
        }
        openApiAuthPO.setRemark(remark);
        openApiAuthMapper.updateByPrimaryKeySelective(openApiAuthPO);
    }

    @Override
    public List<OpenApiAuthPO> queryAccessKeyList(String userId) {
        return openApiAuthMapper.findOne(userId);
    }
}
