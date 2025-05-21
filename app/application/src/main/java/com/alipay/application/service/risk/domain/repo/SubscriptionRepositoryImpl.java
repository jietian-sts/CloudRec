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
package com.alipay.application.service.risk.domain.repo;


import com.alipay.dao.context.UserInfoContext;
import com.alipay.dao.dto.Subscription;
import com.alipay.dao.mapper.SubscriptionActionMapper;
import com.alipay.dao.mapper.SubscriptionMapper;
import com.alipay.dao.po.SubscriptionActionPO;
import com.alipay.dao.po.SubscriptionPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
 *@title SubscriptionRepositoryImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/17 22:36
 */
@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    @Resource
    private SubscriptionMapper subscriptionMapper;

    @Resource
    private SubscriptionActionMapper subscriptionActionMapper;

    @Resource
    private SubscriptionConverter subscriptionConverter;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void save(Subscription subscription) {
        SubscriptionPO subscriptionPO = subscriptionMapper.selectByPrimaryKey(subscription.getId());
        if (subscriptionPO == null) {
            subscription.setUserId(UserInfoContext.getCurrentUser().getUserId());
            subscriptionMapper.insertSelective(subscriptionConverter.toPo(subscription));
        } else {
            subscriptionMapper.updateByPrimaryKeySelective(subscriptionConverter.toPo(subscription));
        }

        // 删除原始配置，创建新的配置
        subscriptionActionMapper.deleteBySubscriptionId(subscription.getId());
        for (Subscription.Action action : subscription.getActionList()) {
            SubscriptionActionPO subscriptionActionPO = new SubscriptionActionPO();
            subscriptionActionPO.setAction(action.getAction());
            subscriptionActionPO.setActionType(action.getActionType());
            subscriptionActionPO.setSubscriptionId(subscription.getId());
            subscriptionActionPO.setName(action.getName());
            subscriptionActionPO.setUrl(action.getUrl());
            subscriptionActionPO.setPeriod(action.getPeriod());
            if (action.getTimeList() != null) {
                subscriptionActionPO.setTimeList(String.join(",", action.getTimeList()));
            }
            subscriptionActionMapper.insertSelective(subscriptionActionPO);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void del(Long id) {
        subscriptionMapper.deleteByPrimaryKey(id);
        subscriptionActionMapper.deleteBySubscriptionId(id);
    }

    @Override
    public Subscription find(Long id) {
        SubscriptionPO subscriptionPO = subscriptionMapper.selectByPrimaryKey(id);
        if (subscriptionPO == null) {
            return null;
        }
        return subscriptionConverter.toEntity(subscriptionPO);
    }
}
