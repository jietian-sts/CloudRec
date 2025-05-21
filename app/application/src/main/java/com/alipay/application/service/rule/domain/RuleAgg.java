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
package com.alipay.application.service.rule.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RuleAgg {

    /**
     * 规则的唯一ID
     */
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String ruleName;

    private String riskLevel;

    private String platform;

    private String resourceType;

    private String userId;

    private Date lastScanTime;

    private Date lastScanTimeStart;

    private Integer isRunning;

    /**
     * 规则状态
     */
    private String status;

    /**
     * 规则描述
     */
    private String ruleDesc;

    /**
     * 上下文模板，用于生成风险上下文信息
     */
    private String context;

    /**
     * 修复建议
     */
    private String advice;

    /**
     * 操作手册链接
     */
    private String link;

    /**
     * 关联数据
     */
    private String linkedDataList;

    /**
     * 唯一code
     */
    private String ruleCode;

    /**
     * 风险数
     */
    private Integer riskCount;

    /**
     * rego 策略
     */
    private String regoPolicy;

    /**
     * 路径
     */
    private String regoPath;

    /**
     * 规则关联的全局变量
     */
    private List<GlobalVariable> globalVariables;

    /**
     * 规则关联的规则组
     */
    private List<RuleGroup> ruleGroups;

    /**
     * 规则类型
     */
    private List<String> ruleTypeList;

    /**
     * 规则关联资产的示例数据
     */
    private String exampleResourceData;


    /**
     * Replace the path of rego to prevent conflicts from overwriting
     */
    public void replace() {
        if (this.regoPolicy != null && this.getId() != null) {
            String newRegoPath = this.getRegoPath() + "_" + this.getId();
            String newrRgoPolicy = this.regoPolicy.replaceFirst("(?<=package )\\S+", newRegoPath);
            this.setRegoPath(newRegoPath);
            this.setRegoPolicy(newrRgoPolicy);
        }
    }

    public void setRunningStartStatus(){
        this.isRunning = 1;
        this.lastScanTimeStart = new Date();
    }

    public void setRunningEndStatus(){
        this.isRunning = 0;
        this.lastScanTime = new Date();
    }


    @Override
    public String toString() {
        return "RuleAgg{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", ruleName='" + ruleName + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", platform='" + platform + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", userId='" + userId + '\'' +
                ", lastScanTime=" + lastScanTime +
                ", status='" + status + '\'' +
                ", ruleDesc='" + ruleDesc + '\'' +
                ", context='" + context + '\'' +
                ", advice='" + advice + '\'' +
                ", link='" + link + '\'' +
                ", linkedDataList='" + linkedDataList + '\'' +
                ", ruleCode='" + ruleCode + '\'' +
                ", riskCount=" + riskCount +
                ", regoPolicy='" + regoPolicy + '\'' +
                ", regoPath='" + regoPath + '\'' +
                ", globalVariables=" + globalVariables +
                ", ruleGroups=" + ruleGroups +
                '}';
    }
}