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
package com.alipay.application.service.resource;

import com.alipay.application.share.request.resource.QueryDetailConfigListRequest;
import com.alipay.application.share.request.resource.SaveDetailConfigRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.resource.ResourceDetailConfigVO;

import java.util.List;
import java.util.Map;

/*
 *@title ResouceDetailConfigService
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/8/12 09:29
 */
public interface ResourceDetailConfigService {


    ApiResponse<String> saveDetailConfig(Map<String, List<SaveDetailConfigRequest>> request);


    ApiResponse<Map<String, List<ResourceDetailConfigVO>>> queryDetailConfigList(QueryDetailConfigListRequest request,
                                                                                 String status);
}
