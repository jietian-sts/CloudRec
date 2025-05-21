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
package com.alipay.application.service.common.repo;


import com.alipay.application.service.common.RunningProgress;
import com.alipay.dao.mapper.RunningProgressMapper;
import com.alipay.dao.po.RunningProgressPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/*
 *@title RunningProgressRepositoryImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/20 11:16
 */
@Repository
public class RunningProgressRepositoryImpl implements RunningProgressRepository {

    @Resource
    private RunningProgressMapper runningProgressMapper;

    @Resource
    private RunningProgressConverter runningProgressConverter;

    @Override
    public long save(RunningProgress runningProgress) {
        RunningProgressPO runningProgressPO = runningProgressMapper.selectByPrimaryKey(runningProgress.getId());
        if (runningProgressPO == null) {
            runningProgressPO = runningProgressConverter.toPo(runningProgress);
            runningProgressMapper.insertSelective(runningProgressPO);
        } else {
            runningProgressPO = runningProgressConverter.toPo(runningProgress);
            runningProgressMapper.updateByPrimaryKeySelective(runningProgressPO);
        }
        return runningProgressPO.getId();
    }

    @Override
    public RunningProgress find(Long id) {
        RunningProgressPO runningProgressPO = runningProgressMapper.selectByPrimaryKey(id);
        return runningProgressConverter.toEntity(runningProgressPO);
    }

    @Override
    public void del(Long id) {
        runningProgressMapper.deleteByPrimaryKey(id);
    }
}
