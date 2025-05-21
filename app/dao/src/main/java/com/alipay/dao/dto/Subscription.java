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
package com.alipay.dao.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/*
 *@title Subscription
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/17 22:33
 */
@Getter
@Setter
public class Subscription {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String name;

    private String condition;

    private String userId;

    private String ruleConfig;

    private String ruleConfigJson;

    private List<Action> actionList;

    private String status;


    @Getter
    @Setter
    public static class Config {
        private Integer id;
        private String key;
        private String keyName;
        private Operator operator;
        private Object value;
    }

    public enum Operator {

        ANY, ALL, EQ, ALL_IN, ANY_IN;
    }

    @Getter
    @Setter
    public static class Action {

        /**
         * 告警类型：定时告警、即时告警
         */
        private String actionType;

        /**
         * 钉钉群、企业微信、接口回调
         */
        private String action;

        /**
         * 群名称
         */
        private String name;

        /**
         * url
         */
        private String url;

        /**
         * 周期：周1、周2、周3...如果是每天都需要告警在不需要填此参数
         */
        private String period;

        /**
         * 告警时间列表10点、12点、14点
         */
        private List<String> timeList;
    }


    public void refresh(String name, String condition,
                        String userId, String ruleConfig,
                        String ruleConfigJson, List<Action> actionList) {
        this.setName(name);
        this.setCondition(condition);
        this.setUserId(userId);
        this.setRuleConfig(ruleConfig);
        this.setRuleConfigJson(ruleConfigJson);
        this.setActionList(actionList);
        this.setGmtModified(new Date());
    }

    public void changeStatus(String status) {
        this.setStatus(status);
        this.setGmtModified(new Date());
    }
}
