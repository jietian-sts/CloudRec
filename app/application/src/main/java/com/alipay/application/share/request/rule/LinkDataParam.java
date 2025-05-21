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
package com.alipay.application.share.request.rule;


import com.alibaba.fastjson.JSON;
import com.alipay.dao.po.CloudResourceInstancePO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
 *@title LinkDataParam
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/24 17:37
 */
@Getter
@Setter
public class LinkDataParam {

    /**
     * 资产类型
     */
    private List<String> resourceType;

    /**
     * 主资产的关联key
     */
    private String linkedKey1;

    /**
     * 关联资产的关联key
     */
    private String linkedKey2;

    /**
     * 挂在点的json key名称
     */
    private String newKeyName;

    /**
     * 是否只挂一条数据
     */
    private String associativeMode;

    /**
     * 挂在对应资产数据列表
     */
    private List<CloudResourceInstancePO> dataList;


    // 反序列化 str => LinkDataParam
    public static List<LinkDataParam> deserializeList(String str) {
        // TODO: Implement deserialization logic
        return JSON.parseArray(str, LinkDataParam.class);
    }
}
