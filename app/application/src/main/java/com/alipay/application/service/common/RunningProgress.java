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


import com.alibaba.fastjson.JSON;
import com.alipay.application.service.common.enums.TaskStatus;
import com.alipay.common.exception.StatusNotFindException;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/*
 *@title RunningProgress
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/20 11:09
 */
@Getter
@Setter
public class RunningProgress {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer total;

    private Integer finishedCount;

    private String result;

    private TaskStatus status;

    private Double percent;

    public static RunningProgress newRunningProgress(Integer total) {
        RunningProgress runningProgress = new RunningProgress();
        runningProgress.setTotal(total);
        runningProgress.setFinishedCount(0);
        runningProgress.setGmtCreate(new Date());
        runningProgress.setGmtModified(new Date());
        runningProgress.setStatus(TaskStatus.running);
        return runningProgress;
    }

    public void cancelTask() {
        this.status = TaskStatus.cancel;
    }

    public boolean isCancel() {
        return this.status.equals(TaskStatus.cancel);
    }

    public void getCancelTaskResponse() {
        if (this.status.equals(TaskStatus.cancel)) {
            Map<String, String> map = Map.of("error", "任务已取消");
            this.result = JSON.toJSONString(map);
            this.finishedCount = this.total;
            this.percent = 1.0;
            return;
        }

        throw new StatusNotFindException();
    }
}
