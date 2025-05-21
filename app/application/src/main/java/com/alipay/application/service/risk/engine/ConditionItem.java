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
package com.alipay.application.service.risk.engine;

import com.alipay.application.service.risk.engine.handler.OperatorHandlerComplete;
import lombok.Getter;

import java.util.List;

@Getter
public class ConditionItem extends Condition {

    private int id;
    private final String key;
    private final Object value;

    public ConditionItem(Operator operator, String key, Object value) {
        super(operator);
        this.key = key;
        this.value = value;
    }

    public ConditionItem(int id, String key, Operator operator, Object value) {
        super(operator);
        this.id = id;
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean match(List<Fact> facts) {
        return OperatorHandlerComplete.handle(this, facts);
    }
}
