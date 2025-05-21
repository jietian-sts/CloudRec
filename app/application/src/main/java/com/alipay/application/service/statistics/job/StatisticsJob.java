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
package com.alipay.application.service.statistics.job;

/*
 *@title StatisticsJob
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/11 17:26
 */
public interface StatisticsJob {

    /**
     * Daily risk statistics
     */
    void dailyRiskManagementStatistics();


    /**
     * Daily statistics
     */
    void historyDataEverydayStatistics();

    /**
     * Asset risk statistics
     */
    void resourceRiskCountStatistics();


    /**
     * Rule scan results statistics
     */
    void ruleScanResultCountStatistics(Long ruleId);


    /**
     * Statistics all
     */
    void statisticsAll();

}
