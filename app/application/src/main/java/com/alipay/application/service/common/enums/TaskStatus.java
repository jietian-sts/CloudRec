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
package com.alipay.application.service.common.enums;

/*
 *@title Status
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/13 17:58
 */
public enum TaskStatus {

    // 有效
    running,

    // 无效
    cancel,

    // 完成
    finished;

    /**
     * 判断
     */
    public static TaskStatus getStatus(String status) {
        for (TaskStatus statusEnum : TaskStatus.values()) {
            if (statusEnum.name().equals(status)) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("无效的状态值: " + status);
    }
}
