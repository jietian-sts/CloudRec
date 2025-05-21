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

import com.alipay.dao.po.ResourceDetailConfigPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ResourceDetailConfigVO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 平台
     */
    private String platform;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 路径
     */
    private String path;

    /**
     * 名称
     */
    private String name;

    /**
     * value
     */
    private Object value;

    /**
     * 状态
     */
    private String status;

    public static ResourceDetailConfigVO build(ResourceDetailConfigPO resourceDetailConfigPO) {
        if (resourceDetailConfigPO != null) {
            ResourceDetailConfigVO resourceDetailConfigVO = new ResourceDetailConfigVO();
            resourceDetailConfigVO.setId(resourceDetailConfigPO.getId());
            resourceDetailConfigVO.setGmtCreate(resourceDetailConfigPO.getGmtCreate());
            resourceDetailConfigVO.setGmtModified(resourceDetailConfigPO.getGmtModified());
            resourceDetailConfigVO.setPlatform(resourceDetailConfigPO.getPlatform());
            resourceDetailConfigVO.setResourceType(resourceDetailConfigPO.getResourceType());
            resourceDetailConfigVO.setPath(resourceDetailConfigPO.getPath());
            resourceDetailConfigVO.setName(resourceDetailConfigPO.getName());
            resourceDetailConfigVO.setStatus(resourceDetailConfigPO.getStatus());
            return resourceDetailConfigVO;
        }
        return null;
    }
}