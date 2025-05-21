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


import com.alibaba.fastjson.JSON;
import com.alipay.dao.mapper.DbCacheMapper;
import com.alipay.dao.po.DbCachePO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/*
 *@title DbCacheUtil
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/13 11:24
 */
@Slf4j
@Component
public class DbCacheUtil {

    @Resource
    private DbCacheMapper dbCacheMapper;

    /**
     * 缓存过期时间，统一设置为3小时
     */
    private static final long CACHE_EXPIRE_TIME = 1000 * 60 * 60 * 3;

    public synchronized void put(String key, Object value) {
        if (Objects.isNull(value)) {
            return;
        }
        try {
            DbCachePO dbCachePO = new DbCachePO();
            dbCachePO.setCacheKey(key);
            dbCachePO.setValue(JSON.toJSONString(value));
            dbCachePO.setCacheTime(new Date());
            dbCacheMapper.insertSelective(dbCachePO);
        } catch (Exception e) {
            log.error("put db cache error, key: {}", key, e);
        }
    }

    public DbCachePO get(String key) {
        DbCachePO dbCachePO = dbCacheMapper.findOne(key);
        if (Objects.isNull(dbCachePO)) {
            return null;
        }

        if (System.currentTimeMillis() - dbCachePO.getCacheTime().getTime() > CACHE_EXPIRE_TIME) {
            dbCacheMapper.delByKey(key);
            return null;
        }

        return dbCachePO;
    }

    public void remove(String key) {
        dbCacheMapper.delByKey(key);
    }

    public void clear() {
        dbCacheMapper.delAll();
    }

    public void clear(String fuzzyKey) {
        dbCacheMapper.delByFuzzyKey(fuzzyKey);
    }

    public long size() {
        return dbCacheMapper.findCount();
    }

    public static String buildKey(Object... str) {
        return String.join("_", Arrays.stream(str).filter(Objects::nonNull).map(Object::toString).toArray(String[]::new));
    }
}
