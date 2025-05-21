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
package com.alipay.application.share.vo.statisics;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
 *@title HomePlatfromResouceDataVO
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/11 13:47
 */
@Getter
@Setter
public class HomePlatformResourceDataVO {

    /**
     * 平台资产总数
     */
    private Long total;

    /**
     * 平台名称
     */
    private String platform;

    /**
     * 资源列表
     */
    List<ResourceData> resouceDataList;

    @Getter
    @Setter
    public static class ResourceData {

        /**
         * 资源类型
         */
        private String resourceType;

        /**
         * 资源数
         */
        private Long count;

        /**
         * 资源组类型名称
         */
        private String resourceGroupTypeName;

        /**
         * 资源组类型
         */
        private String resourceGroupType;

        /**
         * icon
         */
        private String icon;
    }
}
