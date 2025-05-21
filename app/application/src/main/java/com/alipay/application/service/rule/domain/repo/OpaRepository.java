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
package com.alipay.application.service.rule.domain.repo;

import java.util.Map;

/*
 *@title OpaService
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/5 17:16
 */
public interface OpaRepository {

    /**
     * 创建或更新策略
     *
     * @param policyContent 策略内容
     * @return 策略id
     */
    String createOrUpdatePolicy(String policyContent);

    void createOrUpdatePolicy(String path, String policyContent);

    /**
     * 更新全局数据
     *
     * @param path 唯一路径
     * @param data 数据
     */
    void upsertData(String path, Object data);

    /**
     * 获取策略
     *
     * @param id 策略id
     * @return 策略内容
     */
    String getPolicy(String id);

    /**
     * 调用opa服务
     *
     * @param policyContent 策略内容
     * @return 结果
     */
    Map<String, Object> callOpa(String policyContent, String input);

    Map<String, Object> callOpa(String path, String policyContent, String input);

    /**
     * 解析规则路径
     *
     * @param policyContent 策略内容
     * @return 规则路径
     */
    String findPackage(String policyContent);


    /**
     * 解析规则路径
     *
     * @param policyContent 策略内容
     * @param whitedConfigId 白名单配置名称
     * @return 规则路径
     */
    String findWhitedConfigPackage(String policyContent, String whitedConfigId);
}
