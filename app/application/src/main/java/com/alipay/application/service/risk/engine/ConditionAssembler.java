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

/*
 *@title ConditionAssembler
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/9/10 14:55
 */

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ConditionAssembler {

    private static JsonObject assembleCondition(Map<Integer, ConditionItem> conditionItemMap, String expression) {
        JsonObject result = new JsonObject();
        if (expression == null || expression.isEmpty()) {
            if (conditionItemMap.size() != 1) {
                throw new RuntimeException("conditionItemMap size is not 1");
            }
            result.add("condition", parseExpression(conditionItemMap, "1"));
            return result;
        }
        result.add("condition", parseExpression(conditionItemMap, expression));
        return result;
    }

    private static JsonObject parseExpression(Map<Integer, ConditionItem> conditionItemMap, String expression) {
        expression = expression.trim();
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return parseExpression(conditionItemMap, expression.substring(1, expression.length() - 1));
        }

        int index = findMainOperator(expression);

        if (index != -1) {
            String op = expression.substring(index, index + 2);
            String left = expression.substring(0, index);
            String right = expression.substring(index + 2);

            JsonObject result = new JsonObject();
            JsonArray array = new JsonArray();
            array.add(parseExpression(conditionItemMap, left.trim()));
            array.add(parseExpression(conditionItemMap, right.trim()));

            if (op.equals("||")) {
                result.add("ANY", array);
            } else if (op.equals("&&")) {
                result.add("ALL", array);
            }
            return result;
        } else {
            int conditionId = Integer.parseInt(expression.trim());
            ConditionItem conditionItem = conditionItemMap.get(conditionId);
            return getJsonObject(conditionItem);
        }
    }

    private static int findMainOperator(String expression) {
        int level = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(')
                level++;
            else if (c == ')')
                level--;
            else if (level == 0) {
                if (i + 1 < expression.length()) {
                    String sub = expression.substring(i, i + 2);
                    if (sub.equals("||") || sub.equals("&&")) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private static @NotNull JsonObject getJsonObject(ConditionItem conditionItem) {
        JsonObject conditionObject = new JsonObject();
        conditionObject.addProperty("id", conditionItem.getId());
        conditionObject.addProperty("key", conditionItem.getKey());
        conditionObject.addProperty("operator", conditionItem.getOperator().name());
        if (conditionItem.getValue() instanceof String) {
            conditionObject.addProperty("value", (String) conditionItem.getValue());
        } else if (conditionItem.getValue() instanceof Integer) {
            conditionObject.addProperty("value", (Integer) conditionItem.getValue());
        }
        return conditionObject;
    }

    /**
     * 根据条件生成json
     *
     * @param conditionItemMap 条件集合
     * @param expression       表达式
     * @return json
     */
    public static String generateJsonCond(Map<Integer, ConditionItem> conditionItemMap, String expression) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(assembleCondition(conditionItemMap, expression));
    }
}
