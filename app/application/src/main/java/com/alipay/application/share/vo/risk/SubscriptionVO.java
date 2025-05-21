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
package com.alipay.application.share.vo.risk;

import com.alibaba.fastjson.JSON;
import com.alipay.application.service.common.utils.SpringUtils;
import com.alipay.dao.po.SubscriptionPO;
import com.alipay.dao.dto.Subscription;
import com.alipay.application.service.common.enums.SubscriptionType;
import com.alipay.application.service.system.domain.User;
import com.alipay.application.service.system.domain.repo.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class SubscriptionVO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 订阅名称
     */
    private String name;

    /**
     * 订阅类型
     */
    private String actionType;

    /**
     * 创建人
     */
    private String username;

    /**
     * 条件
     */
    private String condition;

    /**
     * 状态
     */
    private String status;

    /**
     * 配置的风险规则
     */
    private List<Subscription.Config> ruleConfig;

    /**
     * 活动列表
     */
    private List<Subscription.Action> actionList;

    public static SubscriptionVO toVo(SubscriptionPO subscriptionPO) {
        if (subscriptionPO == null) {
            return null;
        }

        SubscriptionVO subscriptionVO = new SubscriptionVO();
        subscriptionVO.setId(subscriptionPO.getId());
        subscriptionVO.setGmtCreate(subscriptionPO.getGmtCreate());
        subscriptionVO.setGmtModified(subscriptionPO.getGmtModified());
        subscriptionVO.setName(subscriptionPO.getName());
        subscriptionVO.setCondition(subscriptionPO.getCondition());
        subscriptionVO.setActionList(JSON.parseArray(subscriptionPO.getActionList(), Subscription.Action.class));
        subscriptionVO.setRuleConfig(JSON.parseArray(subscriptionPO.getRuleConfig(), Subscription.Config.class));
        subscriptionVO.setStatus(subscriptionPO.getStatus());
        subscriptionVO.setActionType(getStringBuilder(subscriptionVO).toString());

        UserRepository userRepository = SpringUtils.getApplicationContext().getBean(UserRepository.class);
        User user = userRepository.find(subscriptionPO.getUserId());
        if (user != null) {
            subscriptionVO.setUsername(user.getUsername());
        }
        return subscriptionVO;
    }

    @NotNull
    private static StringBuilder getStringBuilder(SubscriptionVO subscriptionVO) {
        if (subscriptionVO.getActionList() == null) {
            return new StringBuilder();
        }

        StringBuilder builder = new StringBuilder();
        for (Subscription.Action action : subscriptionVO.getActionList()) {
            if (SubscriptionType.timing.name().equals(action.getActionType())) {
                builder.append("定时:");
            }
            if (SubscriptionType.realtime.name().equals(action.getActionType())) {
                builder.append("实时:");
            }

            if (SubscriptionType.Action.dingGroup.name().equals(action.getAction())) {
                builder.append("钉钉群通知");
            }
            if (SubscriptionType.Action.interfaceCallback.name().equals(action.getAction())) {
                builder.append("接口回调");
            }
            if (SubscriptionType.Action.wechat.name().equals(action.getAction())) {
                builder.append("企业微信通知");
            }
            builder.append("\n");
        }
        return builder;
    }
}