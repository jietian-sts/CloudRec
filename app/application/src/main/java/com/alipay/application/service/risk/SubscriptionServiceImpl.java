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

import com.alibaba.fastjson.JSON;
import com.alipay.application.service.common.SubscriptionConfigType;
import com.alipay.application.service.common.enums.CommonStatus;
import com.alipay.application.service.risk.domain.repo.SubscriptionRepository;
import com.alipay.application.service.risk.engine.ConditionAssembler;
import com.alipay.application.service.risk.engine.ConditionItem;
import com.alipay.application.service.risk.engine.Operator;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.risk.SubConfigVO;
import com.alipay.application.share.vo.risk.SubscriptionVO;
import com.alipay.common.exception.BizException;
import com.alipay.dao.dto.Subscription;
import com.alipay.dao.dto.SubscriptionDTO;
import com.alipay.dao.mapper.SubscriptionMapper;
import com.alipay.dao.po.SubscriptionPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 *@title SubscriptionServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/10 17:03
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Resource
    private SubscriptionRepository subscriptionRepository;

    @Resource
    private SubscriptionMapper subscriptionMapper;


    @Override
    public List<SubConfigVO> getSubConfigList() {
        SubscriptionConfigType.initData();

        List<SubConfigVO> list = new ArrayList<>();
        for (SubscriptionConfigType type : SubscriptionConfigType.values()) {
            SubConfigVO subConfigVO = new SubConfigVO();
            subConfigVO.setKey(type.name());
            subConfigVO.setKeyName(type.getKeyName());
            subConfigVO.setOperatorList(type.getOperatorList());
            subConfigVO.setValue(type.getValue());
            list.add(subConfigVO);
        }
        return list;
    }

    @Override
    public void saveConfig(SubscriptionDTO dto) {
        Map<Integer, ConditionItem> conditionItemMap = new HashMap<>();
        List<Subscription.Config> ruleConfigList = dto.getRuleConfigList();
        for (Subscription.Config config : ruleConfigList) {
            conditionItemMap.put(config.getId(), new ConditionItem(config.getId(), config.getKey(), Operator.valueOf(config.getOperator().name()), config.getValue()));
        }

        try {
            String ruleConfigJson = ConditionAssembler.generateJsonCond(conditionItemMap, dto.getCondition());
            dto.setRuleConfigJson(ruleConfigJson);
        } catch (Exception e) {
            throw new RuntimeException(dto.getCondition() + " condition is not valid");
        }

        if (dto.getId() != null) {
            Subscription subscription = subscriptionRepository.find(dto.getId());
            if (subscription == null) {
                throw new RuntimeException("subscription id is not exist");
            }

            subscription.refresh(dto.getName(), dto.getCondition(), dto.getUserId(), JSON.toJSONString(dto.getRuleConfigList()), dto.getRuleConfigJson(), dto.getActionList());
            subscriptionRepository.save(subscription);
        } else {
            Subscription subscription = new Subscription();
            subscription.refresh(dto.getName(), dto.getCondition(), dto.getUserId(), JSON.toJSONString(dto.getRuleConfigList()), dto.getRuleConfigJson(), dto.getActionList());
            subscription.setStatus(CommonStatus.valid.name());
            subscriptionRepository.save(subscription);
        }
    }

    @Override
    public SubscriptionVO getSubscriptionDetail(Long id) {
        SubscriptionPO subscription = subscriptionMapper.selectByPrimaryKey(id);
        if (subscription == null) {
            throw new RuntimeException("subscription not exist!");
        }

        return SubscriptionVO.toVo(subscription);
    }

    @Override
    public ListVO<SubscriptionVO> getSubscriptionList(SubscriptionDTO subscriptionDTO) {
        ListVO<SubscriptionVO> listVO = new ListVO<>();
        int count = subscriptionMapper.count(subscriptionDTO);
        if (count == 0) {
            return listVO;
        }

        subscriptionDTO.setOffset();
        List<SubscriptionPO> list = subscriptionMapper.list(subscriptionDTO);
        List<SubscriptionVO> collect = list.stream().map(SubscriptionVO::toVo).collect(Collectors.toList());
        listVO.setTotal(count);
        listVO.setData(collect);
        return listVO;
    }

    @Override
    public void deleteSubscription(Long id) {
        subscriptionRepository.del(id);
    }

    @Override
    public void changeStatus(Long id, String status) {
        Subscription subscription = subscriptionRepository.find(id);
        if (subscription == null) {
            throw new BizException("Invalid subscription ID: " + id);
        }

        subscription.changeStatus(status);
        subscriptionRepository.save(subscription);
    }
}
