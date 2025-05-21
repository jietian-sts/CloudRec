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
package com.alipay.application.service.collector.domain.repo;


import com.alipay.application.service.collector.domain.Agent;
import com.alipay.dao.mapper.AgentRegistryMapper;
import com.alipay.dao.po.AgentRegistryPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/*
 *@title AgentRegistry
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/20 10:43
 */
@Repository
public class AgentRepositoryImpl implements AgentRepository {


    @Resource
    private AgentRegistryMapper agentRegistryMapper;

    @Resource
    private AgentConverter agentConverter;

    @Override
    public Agent find(Long id) {
        AgentRegistryPO agentRegistryPO = agentRegistryMapper.selectByPrimaryKey(id);
        return agentConverter.toEntity(agentRegistryPO);
    }

    @Override
    public void save(Agent agent) {
        AgentRegistryPO agentRegistryPO = agentRegistryMapper.selectByPrimaryKey(agent.getId());
        if (agentRegistryPO == null) {
            agentRegistryMapper.insertSelective(agentConverter.toPo(agent));
        } else {
            agentRegistryMapper.updateByPrimaryKeySelective(agentConverter.toPo(agent));
        }
    }

    @Override
    public void del(Long id) {
        agentRegistryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Agent find(String platform, String value) {
        AgentRegistryPO agentRegistryPO = agentRegistryMapper.findOne(platform, value);
        return agentConverter.toEntity(agentRegistryPO);
    }
}
