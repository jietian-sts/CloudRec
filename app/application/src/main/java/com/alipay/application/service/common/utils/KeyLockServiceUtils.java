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


import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 *@title KeyLockServiceUtils
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/12/30 15:34
 */
@Component
public class KeyLockServiceUtils {

    private final ConcurrentHashMap<String, Lock> lockMap = new ConcurrentHashMap<>();

    public void lockForKey(String key) {
        Lock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());
        lock.lock();
    }

    public void unlockForKey(String key) {
        Lock lock = lockMap.get(key);
        if (lock != null) {
            lock.unlock();
            // Optional: Remove the lock if it is no longer needed
            if (!lock.tryLock()) { // Ensure no one else is holding the lock
                lockMap.remove(key, lock);
            } else {
                lock.unlock(); // Re-lock and unlock to maintain state
            }
        }
    }

    public <T> T executeWithLock(String key, Callable<T> action) throws Exception {
        lockForKey(key);
        try {
            return action.call();
        } finally {
            unlockForKey(key);
        }
    }

    public static void main(String[] args) throws Exception {


        KeyLockServiceUtils keyLockServiceUtils = new KeyLockServiceUtils();


        for (int i = 0; i < 10; i++) {

            String r1 = keyLockServiceUtils.executeWithLock("test", () -> {
                System.out.println("111111 执行");
                Thread.sleep(10000);
                return "111111";
            });

        }

    }
}
