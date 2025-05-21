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
import com.alipay.dao.dto.RuleDTO;
import com.alipay.dao.mapper.RuleMapper;
import com.alipay.dao.po.RuleGroupPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class RuleGroupVO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 规则组名称
     */
    private String groupName;

    /**
     * 描述
     */
    private String groupDesc;

    /**
     * 规则数
     */
    private String ruleCount;

    /**
     * 创建人
     */
    private String username;

    /**
     * 最近扫描开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date LastScanStartTime;

    /**
     * 最近扫描结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastScanEndTime;


    private List<RuleVO> aboutRuleList;


    private Boolean disabled;

    public static RuleGroupVO build(RuleGroupPO ruleGroupPO) {
        if (ruleGroupPO == null) {
            return null;
        }

        RuleGroupVO ruleGroupVO = new RuleGroupVO();
        BeanUtils.copyProperties(ruleGroupPO, ruleGroupVO);

        // Query the number of rules
        RuleMapper ruleMapper = SpringUtils.getApplicationContext().getBean(RuleMapper.class);

        RuleDTO ruleDTO = RuleDTO.builder().ruleGroupId(ruleGroupPO.getId()).build();
        int count = ruleMapper.findCount(ruleDTO);
        ruleGroupVO.setRuleCount(String.valueOf(count));

        return ruleGroupVO;
    }

    public static RuleGroupVO buildEasy(RuleGroupPO ruleGroupPO) {
        RuleGroupVO ruleGroupVO = new RuleGroupVO();
        BeanUtils.copyProperties(ruleGroupPO, ruleGroupVO);
        return ruleGroupVO;
    }
}