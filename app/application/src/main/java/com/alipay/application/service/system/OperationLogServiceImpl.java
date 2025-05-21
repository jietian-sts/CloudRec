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
package com.alipay.application.service.system;

import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.system.OperationLogVO;
import com.alipay.common.enums.Action;
import com.alipay.common.enums.LogType;
import com.alipay.dao.dto.OperationLogDTO;
import com.alipay.dao.mapper.OperationLogMapper;
import com.alipay.dao.po.OperationLogPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 *@title OperationLogServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/8/21 11:45
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public ApiResponse<List<OperationLogVO>> queryOperationLog(OperationLogDTO operationLogDTO) {
        List<OperationLogPO> list = operationLogMapper.findList(operationLogDTO);
        List<OperationLogVO> result = new ArrayList<>(list.stream().map(OperationLogVO::build).toList());

        return new ApiResponse<>(result);
    }

    @Override
    public ApiResponse<String> commentInformation(OperationLogDTO operationLogDTO) {
        OperationLogPO operationLogPO = new OperationLogPO();
        operationLogPO.setUserId(operationLogDTO.getUserId());
        operationLogPO.setCorrelationId(operationLogDTO.getCorrelationId());
        operationLogPO.setType(LogType.RISK.name());
        operationLogPO.setAction(Action.RiskAction.ADD_NOTE.getName());
        operationLogPO.setNotes(operationLogDTO.getNotes());
        operationLogMapper.insertSelective(operationLogPO);

        return ApiResponse.SUCCESS;
    }
}
