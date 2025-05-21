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
package com.alipay.application.service.common.utils;


import com.google.common.cache.Cache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/*
 *@title CacheUtil
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/12/17 22:58
 */
@Component
public class CacheUtil<K, V> {

    @Resource
    private Cache<K, V> cache;

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public V get(K key) {
        return cache.getIfPresent(key);
    }

    public void remove(K key) {
        cache.invalidate(key);
    }

    public void clear() {
        cache.invalidateAll();
    }

    public long size() {
        return cache.size();
    }

    public static String buildKey(Object... str) {
        return String.join("_", Arrays.stream(str).filter(Objects::nonNull).map(Object::toString).toArray(String[]::new));
    }
}
