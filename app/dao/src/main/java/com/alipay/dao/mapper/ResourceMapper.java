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
package com.alipay.dao.mapper;

import com.alipay.dao.po.ResourcePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ResourcePO record);

    ResourcePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResourcePO record);

    ResourcePO findOne(@Param("platform") String platform, @Param("resourceType") String resourceType);

    /**
     * 查询某一平台下的所有资源类型
     *
     * @param platform 平台
     * @return 资源类型列表
     */
    List<ResourcePO> findByPlatform(String platform);

    /**
     * 查询某一类资产的资源类型列表
     *
     * @param platformList      云平台list
     * @param resourceGroupType eg：存储、身份、计算、AI...
     * @return 资源类型列表
     */
    List<ResourcePO> findByGroupType(@Param("platformList") List<String> platformList,
                                     @Param("resourceGroupType") String resourceGroupType);

    List<ResourcePO> findAll();
}