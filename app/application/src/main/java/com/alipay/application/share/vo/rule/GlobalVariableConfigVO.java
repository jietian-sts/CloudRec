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
package com.alipay.application.share.vo.rule;

import com.alipay.application.service.common.utils.SpringUtils;
import com.alipay.dao.mapper.GlobalVariableConfigMapper;
import com.alipay.dao.po.GlobalVariableConfigPO;
import com.alipay.dao.po.RulePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GlobalVariableConfigVO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 变量名
     */
    private String name;

    /**
     * 变量路径
     */
    private String path;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 版本
     */
    private String version;

    /**
     * 状态
     */
    private String status;

    /**
     * json 数据
     */
    private String data;

    /**
     * 关联规则列表
     */
    private List<String> ruleNameList;

    public static GlobalVariableConfigVO build(GlobalVariableConfigPO globalVariableConfigPO) {
        if (globalVariableConfigPO == null) {
            return null;
        }
        GlobalVariableConfigVO globalVariableConfigVO = new GlobalVariableConfigVO();
        BeanUtils.copyProperties(globalVariableConfigPO, globalVariableConfigVO);

        GlobalVariableConfigMapper globalVariableConfigMapper = SpringUtils.getApplicationContext()
                .getBean(GlobalVariableConfigMapper.class);
        List<RulePO> rulePOS = globalVariableConfigMapper.findRelRuleList(globalVariableConfigPO.getId());
        globalVariableConfigVO.setRuleNameList(rulePOS.stream().map(RulePO::getRuleName).collect(Collectors.toList()));

        return globalVariableConfigVO;
    }
}