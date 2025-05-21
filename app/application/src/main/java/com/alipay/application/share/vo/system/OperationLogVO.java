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
package com.alipay.application.share.vo.system;

import com.alipay.application.service.common.utils.SpringUtils;
import com.alipay.dao.mapper.UserMapper;
import com.alipay.dao.po.OperationLogPO;
import com.alipay.dao.po.UserPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Setter
@Getter
public class OperationLogVO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 动作
     */
    private String action;

    /**
     * 关联id
     */
    private Long correlationId;

    /**
     * 备注
     */
    private String notes;

    public static OperationLogVO build(OperationLogPO operationLogPO) {
        if (operationLogPO == null) {
            return null;
        }

        OperationLogVO operationLogVO = new OperationLogVO();
        BeanUtils.copyProperties(operationLogPO, operationLogVO);

        UserMapper userMapper = SpringUtils.getApplicationContext().getBean(UserMapper.class);
        UserPO userPO = userMapper.findOne(operationLogPO.getUserId());
        if (userPO != null) {
            operationLogVO.setUsername(userPO.getUsername());
        }

        return operationLogVO;
    }
}