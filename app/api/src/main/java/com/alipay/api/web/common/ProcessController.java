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
package com.alipay.api.web.common;


import com.alipay.application.service.common.RunningProgressService;
import com.alipay.application.share.request.rule.CancelTaskRequest;
import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.common.GetProgressVO;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 *@title ProcessController
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/14 15:43
 */
@RestController
@RequestMapping("/api/progress")
public class ProcessController {

    @Resource
    private RunningProgressService runningProgress;

    @GetMapping("/getProgress")
    public ApiResponse<GetProgressVO> getProgress(@RequestParam Long taskId) {
        GetProgressVO progressVO = runningProgress.query(taskId);
        return new ApiResponse<>(progressVO);
    }

    @PostMapping("/cancelTask")
    public ApiResponse<String> cancelTask(@RequestBody @Validated CancelTaskRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return new ApiResponse<>(result);
        }
        runningProgress.cancelTask(request.getTaskId());
        return ApiResponse.SUCCESS;
    }
}
