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

import com.alibaba.fastjson.JSON;
import com.alipay.application.service.resource.ResourceDetailConfigService;
import com.alipay.application.share.request.resource.QueryDetailConfigListRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.service.common.utils.SpringUtils;
import com.alipay.common.enums.Status;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.mapper.TenantMapper;
import com.alipay.dao.po.CloudAccountPO;
import com.alipay.dao.po.CloudResourceInstancePO;
import com.alipay.dao.po.TenantPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ResourceInstanceVO {

    /**
     * id
     */
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 平台
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
     * 资源类型
     */
    private String resourceType;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 区域
     */
    private String regionId;

    /**
     * 地址
     */
    private String address;

    /**
     * 实例对象
     */
    private Map<String, Object> instance;

    private Map<String, List<ResourceDetailConfigVO>> resourceDetailConfigMap;

    public static ResourceInstanceVO build(CloudResourceInstancePO resourceInstance) {
        if (resourceInstance == null) {
            return null;
        }

        ResourceInstanceVO resourceInstanceVO = new ResourceInstanceVO();
        BeanUtils.copyProperties(resourceInstance, resourceInstanceVO);
        resourceInstanceVO.setId(resourceInstance.getId().toString());
        resourceInstanceVO.setInstance(JSON.parseObject(resourceInstance.getInstance(), Map.class));
        // Parse asset details
        if (resourceInstance.getInstance() != null) {
            ResourceDetailConfigService resourceDetailConfigService = SpringUtils.getApplicationContext()
                    .getBean(ResourceDetailConfigService.class);
            QueryDetailConfigListRequest queryDetailConfigListRequest = QueryDetailConfigListRequest.builder()
                    .resourceIdEq(resourceInstance.getResourceId()).platform((resourceInstance.getPlatform()))
                    .resourceType(resourceInstance.getResourceType()).build();
            ApiResponse<Map<String, List<ResourceDetailConfigVO>>> apiResponse = resourceDetailConfigService
                    .queryDetailConfigList(queryDetailConfigListRequest, Status.valid.name());
            if (apiResponse.getCode() == ApiResponse.SUCCESS_CODE) {
                resourceInstanceVO.setResourceDetailConfigMap(apiResponse.getContent());
            }
        }

        CloudAccountMapper cloudAccountMapper = SpringUtils.getApplicationContext().getBean(CloudAccountMapper.class);
        TenantMapper tenantMapper = SpringUtils.getApplicationContext().getBean(TenantMapper.class);
        CloudAccountPO cloudAccountPO = cloudAccountMapper.findByCloudAccountId(resourceInstance.getCloudAccountId());
        if (cloudAccountPO != null) {
            TenantPO tenantPO = tenantMapper.selectByPrimaryKey(cloudAccountPO.getTenantId());
            resourceInstanceVO.setTenantName(tenantPO != null ? tenantPO.getTenantName() : "");
        }

        return resourceInstanceVO;
    }
}
