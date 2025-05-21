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
import com.alipay.application.service.risk.engine.Operator;

import java.util.Collection;
import java.util.List;

public class AnyInHandler extends AbstractHanlder {
    @Override
    public boolean canHandle(ConditionItem conditionItem) {
        return Operator.ANY_IN == conditionItem.getOperator();
    }

//    @Override
//    public boolean doHandle(ConditionItem conditionItem, Fact fact) {
//        if (conditionItem.getValue() instanceof Collection && fact.getValue() instanceof Collection) {
//            Collection expected = (Collection) conditionItem.getValue();
//            Collection factValue = (Collection) fact.getValue();
//            return factValue.stream().anyMatch(o -> expected.contains(o));
//        }
//        return false;
//    }

    // 用户配置的值在可选范围内
    @Override
    public boolean doHandle(ConditionItem conditionItem, Fact fact) {
        if (fact.getValue() instanceof Collection) {
            Collection factValue = (Collection) fact.getValue();
            List list = factValue.stream().map(Object::toString).toList();
            return list.contains(String.valueOf(conditionItem.getValue()));
        }
        return false;
    }
}
