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
package com.alipay.application.service.risk;

import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.risk.SubConfigVO;
import com.alipay.application.share.vo.risk.SubscriptionVO;
import com.alipay.dao.dto.SubscriptionDTO;

import java.util.List;

/*
 *@title SubscriptionService
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/10 16:58
 */
public interface SubscriptionService {


    List<SubConfigVO> getSubConfigList();


    void saveConfig(SubscriptionDTO subscriptionDTO);


    SubscriptionVO getSubscriptionDetail(Long id);


    ListVO<SubscriptionVO> getSubscriptionList(SubscriptionDTO subscriptionDTO);


    void deleteSubscription(Long id);


    void changeStatus(Long id, String status);
}
