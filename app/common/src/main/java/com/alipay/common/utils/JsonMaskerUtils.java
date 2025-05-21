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
package com.alipay.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;

/**
 * Replace value in json as a random string
 */
public class JsonMaskerUtils {
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String maskSensitiveData(String json) throws Exception {
        if (json == null || json.isEmpty()) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        return mapper.writeValueAsString(processNode(root));
    }

    private static JsonNode processNode(JsonNode node) {
        if (node.isObject()) {
            return processObject((ObjectNode) node);
        } else if (node.isArray()) {
            return processArray((ArrayNode) node);
        } else if (node.isTextual()) {
            return new TextNode(generateMask(node.asText().length()));
        }
        return node;
    }

    private static ObjectNode processObject(ObjectNode node) {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            node.set(entry.getKey(), processNode(entry.getValue()));
        }
        return node;
    }

    private static ArrayNode processArray(ArrayNode node) {
        for (int i = 0; i < node.size(); i++) {
            node.set(i, processNode(node.get(i)));
        }
        return node;
    }

    private static String generateMask(int length) {
        if (length <= 0) return "";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length())));
        }
        return sb.toString();
    }
}