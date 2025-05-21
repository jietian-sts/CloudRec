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
package com.alipay.application.service.risk.engine.handler;

import com.alipay.application.service.risk.engine.ConditionItem;
import com.alipay.application.service.risk.engine.Fact;

import java.util.List;

public abstract class AbstractHanlder implements OperatorHandler {

    @Override
    public boolean handle(ConditionItem conditionItem, List<Fact> facts) {
        return canHandle(conditionItem) && facts.stream()
                .anyMatch(fact -> conditionItem.getKey().equals(fact.getKey()) && doHandle(conditionItem, fact));
    }

    /**
     * 是否能处理操作
     *
     * @param conditionItem
     * @return
     */
    protected abstract boolean canHandle(ConditionItem conditionItem);

    /**
     * 处理操作
     *
     * @param conditionItem
     * @param fact
     * @return
     */
    protected abstract boolean doHandle(ConditionItem conditionItem, Fact fact);
}
