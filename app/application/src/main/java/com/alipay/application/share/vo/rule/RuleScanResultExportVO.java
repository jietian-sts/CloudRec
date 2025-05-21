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

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alipay.application.service.common.utils.SpringUtils;
import com.alipay.common.utils.DateUtil;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.mapper.RuleMapper;
import com.alipay.dao.po.CloudAccountPO;
import com.alipay.dao.po.RulePO;
import com.alipay.dao.po.RuleScanResultPO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
@Data
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class RuleScanResultExportVO {


    @ExcelProperty(value = "首次发现时间", index = 0)
    private String gmtCreate;

    @ExcelProperty(value = "最近发现时间", index = 1)
    private String updateTime;

    @ExcelProperty(value = "云账号ID", index = 2)
    private String cloudAccountId;

    @ExcelProperty(value = "云账号别名", index = 3)
    private String alias;

    @ExcelProperty(value = "资源id", index = 4)
    private String resourceId;

    @ExcelProperty(value = "资源名称", index = 5)
    private String resourceName;

    @ExcelProperty(value = "平台", index = 6)
    private String platform;

    @ExcelProperty(value = "规则名称", index = 7)
    private String ruleName;

    @ExcelProperty(value = "风险等级", index = 8)
    private String riskLevel;

    @ExcelProperty(value = "资产类型", index = 9)
    private String resourceType;

    @ExcelProperty(value = "扫描结果", index = 10)
    private String result;

    @ExcelProperty(value = "region", index = 11)
    private String region;

    @ExcelProperty(value = "状态", index = 12)
    private String status;

    @ExcelProperty(value = "忽略的原因类型", index = 13)
    private String ignoreReasonType;

    @ExcelProperty(value = "忽略的原因", index = 14)
    private String ignoreReason;

    @ExcelProperty(value = "规则快照", index = 15)
    private String ruleSnapshoot;


    public static RuleScanResultExportVO po2vo(RuleScanResultPO ruleScanResultPO) {
        if (ruleScanResultPO == null) {
            return null;
        }

        RuleScanResultExportVO ruleScanResultVO = new RuleScanResultExportVO();
        BeanUtils.copyProperties(ruleScanResultPO, ruleScanResultVO);
        ruleScanResultVO.setGmtCreate(DateUtil.dateToString(ruleScanResultPO.getGmtCreate()));

        // Association Rules
        RuleMapper ruleMapper = SpringUtils.getApplicationContext().getBean(RuleMapper.class);
        RulePO rulePO = ruleMapper.selectByPrimaryKey(ruleScanResultPO.getRuleId());
        if (rulePO != null) {
            ruleScanResultVO.setRuleName(rulePO.getRuleName());
            ruleScanResultVO.setRiskLevel(rulePO.getRiskLevel());
        }

        // Query account information
        CloudAccountMapper cloudAccountMapper = SpringUtils.getApplicationContext().getBean(CloudAccountMapper.class);
        CloudAccountPO cloudAccountPO = cloudAccountMapper.findByCloudAccountId(ruleScanResultPO.getCloudAccountId());
        if (cloudAccountPO != null) {
            ruleScanResultVO.setAlias(cloudAccountPO.getAlias());
        }

        return ruleScanResultVO;
    }
}