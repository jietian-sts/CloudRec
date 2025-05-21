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
package com.alipay.application.service.common;


import com.alipay.application.share.vo.common.GetProgressVO;
import com.alipay.application.service.common.enums.TaskStatus;
import com.alipay.application.service.common.repo.RunningProgressRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/*
 *@title RunningProgress
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/14 14:17
 */
@Component
public class RunningProgressServiceImpl implements RunningProgressService {

    @Resource
    private RunningProgressRepository runningProgressRepository;


    public Long init(int total) {
        RunningProgress runningProgress = RunningProgress.newRunningProgress(total);
        return runningProgressRepository.save(runningProgress);
    }


    public Double queryPercent(Long id) {
        RunningProgress runningProgress = runningProgressRepository.find(id);
        if (runningProgress.getTotal() == 0) {
            return 1.0;
        }

        double result = (double) runningProgress.getFinishedCount() / runningProgress.getTotal();
        return Double.parseDouble(String.format("%.4f", result));
    }

    @Override
    public void cancelTask(Long taskId) {
        RunningProgress runningProgress = runningProgressRepository.find(taskId);
        runningProgress.cancelTask();
        runningProgressRepository.save(runningProgress);
    }

    public GetProgressVO query(Long id) {
        RunningProgress runningProgress = runningProgressRepository.find(id);
        if (runningProgress.getStatus().equals(TaskStatus.cancel)) {
            runningProgress.getCancelTaskResponse();
            GetProgressVO getProgressVO = new GetProgressVO();
            BeanUtils.copyProperties(runningProgress, getProgressVO);
            return getProgressVO;
        }

        GetProgressVO getProgressVO = new GetProgressVO();
        BeanUtils.copyProperties(runningProgress, getProgressVO);
        getProgressVO.setPercent(queryPercent(id));
        return getProgressVO;
    }

    // 更新进度
    @Override
    public RunningProgress update(Long id, int finishedCount, String result) {
        RunningProgress runningProgress = runningProgressRepository.find(id);
        if (Objects.isNull(runningProgress)) {
            return null;
        }

        if (finishedCount >= runningProgress.getTotal()) {
            finishedCount = runningProgress.getTotal();
        }
        runningProgress.setFinishedCount(finishedCount);
        runningProgress.setResult(result);
        runningProgressRepository.save(runningProgress);

        return runningProgress;
    }

    public void delete(Long id) {
        runningProgressRepository.del(id);
    }
}
