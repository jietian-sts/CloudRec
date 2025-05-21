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
 *@title Subscription
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/20 16:28
 */
public enum SubscriptionType {
    realtime, timing;

    /**
     * 判断是否包含
     */
    public static boolean contains(String type) {
        for (SubscriptionType value : SubscriptionType.values()) {
            if (value.name().equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取枚举
     *
     * @param type 枚举名称
     * @return 枚举
     */
    public static SubscriptionType getName(String type) {
        for (SubscriptionType value : SubscriptionType.values()) {
            if (value.name().equals(type)) {
                return value;
            }
        }
        throw new RuntimeException("invalid subscription type: " + type);
    }

    public static enum Action {
        dingGroup, wechat, interfaceCallback;

        public static boolean contains(String type) {
            for (Action value : Action.values()) {
                if (value.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }

        public static Action getName(String type) {
            for (Action action : Action.values()) {
                if (action.name().equals(type)) {
                    return action;
                }
            }
            throw new RuntimeException("invalid action type: " + type);
        }

    }
}
