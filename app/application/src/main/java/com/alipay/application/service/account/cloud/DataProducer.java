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
package com.alipay.application.service.account.cloud;


/*
 *@title SecurityProductManage
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 16:41
 */
public interface DataProducer {


    /**
     * 加工 iam 身份统计数据
     */
    void productIamStatisticsData();

    /**
     * 加工安全产品统计数据
     */
    void productSecurityProductStatisticsData();
}
