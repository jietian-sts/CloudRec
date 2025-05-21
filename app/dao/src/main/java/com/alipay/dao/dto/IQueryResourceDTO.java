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
package com.alipay.dao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/*
 *@title IQueryResourceDTO
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/11/11 12:29
 */

@Builder
@Getter
@Setter
public class IQueryResourceDTO{

    private String id;

    private Long scrollId;

    private String platform;

    private List<String> platformList;

    private Long tenantId;

    private String resourceType;

    private List<String> resourceTypeList;

    private String cloudAccountId;

    private List<String> cloudAccountIdList;

    private String alias;

    private String resourceId;

    private String resourceIdEq;

    private String resourceName;

    private String searchParam;

    private String address;

    private String startDate;

    private String endDate;

    private String instance;

    private String customFieldValue;

    /**
     * Used to sort by a specific field
     */
    private String sortParam;

    /**
     * ASC OR DESC
     */
    private String sortType;

    private Date gmtCreateEnd;


    private Integer size;

    private Integer page;

    private Integer offset;

    {
        this.size = 10;
        this.page = 1;
    }
}
