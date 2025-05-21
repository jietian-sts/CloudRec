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

import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DBDistributedLockUtil {

    @Resource
    private JdbcTemplate jdbcTemplate;

    // 用于存储当前线程持有的锁信息（防止重复获取）
    private final ConcurrentHashMap<String, LockInfo> currentLocks = new ConcurrentHashMap<>();


    public static String getHostName() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }


    /**
     * 尝试获取分布式锁
     *
     * @param taskName   任务名称（唯一标识）
     * @param expireTime 锁过期时间（毫秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String taskName, long expireTime) {
        // 1. 尝试直接插入新锁记录
        String hostname = getHostName();
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int affectedRows = jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO local_task_locks (task_name, execute_host) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, taskName);
                ps.setString(2, hostname);
                return ps;
            }, keyHolder);

            if (affectedRows > 0) {
                currentLocks.put(taskName, new LockInfo(taskName, hostname, System.currentTimeMillis()));
                return true;
            }
        } catch (DuplicateKeyException e) {
            // 2. 锁已存在，检查是否过期
            return handleExistingLock(taskName, hostname, expireTime);
        }
        return false;
    }

    /**
     * 处理已存在的锁记录
     */
    private boolean handleExistingLock(String taskName, String host, long expireTime) {
        // 查询现有锁信息
        List<LockRecord> records = jdbcTemplate.query(
                "SELECT execute_host, gmt_modified FROM local_task_locks WHERE task_name = ?",
                (rs, rowNum) -> new LockRecord(
                        rs.getString("execute_host"),
                        rs.getTimestamp("gmt_modified").toInstant()
                ),
                taskName
        );

        if (records.isEmpty()) {
            return false;
        }

        LockRecord existingLock = records.get(0);
        Instant lastModified = existingLock.gmtModified();
        Instant now = Instant.now();

        // 判断锁是否过期
        if (Duration.between(lastModified, now).toMillis() > expireTime) {
            // 尝试抢占过期锁（CAS更新）
            int updated = jdbcTemplate.update(
                    "UPDATE local_task_locks SET execute_host = ?, gmt_modified = ? " +
                            "WHERE task_name = ? AND gmt_modified = ?",
                    host, Timestamp.from(now),
                    taskName, Timestamp.from(lastModified)
            );

            if (updated > 0) {
                currentLocks.put(taskName, new LockInfo(taskName, host, System.currentTimeMillis()));
                return true;
            }
        }
        return false;
    }


    /**
     * 续期锁有效期
     */
    public boolean renewLock(String taskName, String host, long expireTime) {
        LockInfo lockInfo = currentLocks.get(taskName);
        if (lockInfo == null || !lockInfo.getHost().equals(host)) {
            return false;
        }

        // 检查是否超过续期间隔（例如至少续期间隔为 expireTime/3）
        long now = System.currentTimeMillis();
        if (now - lockInfo.getLastRenewTime() < expireTime / 3) {
            return true; // 未到续期时间
        }

        int updated = jdbcTemplate.update(
                "UPDATE local_task_locks SET gmt_modified = CURRENT_TIMESTAMP " +
                        "WHERE task_name = ?",
                taskName
        );

        if (updated > 0) {
            lockInfo.updateRenewTime(now);
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     */
    public void releaseLock(String taskName) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM local_task_locks WHERE task_name = ?",
                    taskName
            );
        } finally {
            currentLocks.remove(taskName);
        }
    }


    // 辅助记录类
    private record LockRecord(String executeHost, Instant gmtModified) {
    }

    private static class LockInfo {
        private final String taskName;
        private final String host;
        private final long expireTime;
        private volatile long lastRenewTime;

        LockInfo(String taskName, String host, long expireTime) {
            this.taskName = taskName;
            this.host = host;
            this.expireTime = expireTime;
            this.lastRenewTime = System.currentTimeMillis();
        }


        void updateRenewTime(long time) {
            this.lastRenewTime = time;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getHost() {
            return host;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public long getLastRenewTime() {
            return lastRenewTime;
        }

    }
}