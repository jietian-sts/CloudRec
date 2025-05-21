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
import com.alipay.application.service.common.enums.TaskStatus;
import com.alipay.dao.converter.Converter;
import com.alipay.dao.po.RunningProgressPO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/*
 *@title RunningProgressConverter
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/20 11:17
 */
@Component
public class RunningProgressConverter implements Converter<RunningProgress, RunningProgressPO> {
    @Override
    public RunningProgressPO toPo(RunningProgress runningProgress) {
        RunningProgressPO runningProgressPO = new RunningProgressPO();
        BeanUtils.copyProperties(runningProgress, runningProgressPO);
        runningProgressPO.setStatus(runningProgress.getStatus().name());
        return runningProgressPO;
    }

    @Override
    public RunningProgress toEntity(RunningProgressPO runningProgressPO) {
        if (runningProgressPO == null) {
            return null;
        }
        RunningProgress runningProgress = new RunningProgress();
        BeanUtils.copyProperties(runningProgressPO, runningProgress);
        runningProgress.setStatus(TaskStatus.getStatus(runningProgressPO.getStatus()));
        return runningProgress;
    }
}
