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
import com.alipay.dao.mapper.UserMapper;
import com.alipay.dao.po.RulePO;
import com.alipay.dao.po.RuleRegoPO;
import com.alipay.dao.po.UserPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * rego返回信息
 */
@Data
public class RuleRegoVO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 1-草稿，0-正式
     */
    private Integer isDraft;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 平台
     */
    private String platform;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 规则内容
     */
    private String ruleRego;

    /**
     * 示例数据
     */
    private String input;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    public static RuleRegoVO build(RuleRegoPO ruleRegoPO) {
        if (ruleRegoPO == null) {
            return null;
        }
        RuleRegoVO ruleRegoVO = new RuleRegoVO();
        BeanUtils.copyProperties(ruleRegoPO, ruleRegoVO);

        UserMapper userMapper = SpringUtils.getApplicationContext().getBean(UserMapper.class);
        if (ruleRegoPO.getUserId() != null) {
            UserPO userPO = userMapper.findOne(ruleRegoPO.getUserId());
            if (userPO != null) {
                ruleRegoVO.setUserName(userPO.getUsername());
            }
        }

        return ruleRegoVO;
    }

    public static RuleRegoVO build(RulePO rulePO) {
        if (rulePO == null) {
            return null;
        }
        RuleRegoVO ruleRegoVO = new RuleRegoVO();
        ruleRegoVO.setPlatform(rulePO.getPlatform());
        ruleRegoVO.setResourceType(rulePO.getResourceType());
        return ruleRegoVO;
    }
}