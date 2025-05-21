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

import java.util.*;
import java.util.function.Function;

public class Util {
    private static Random random = new Random();

    public static <T> List<T> array(T... args) {
        if (args == null || args.length == 0) {
            return new ArrayList<>(0);
        }

        List<T> ret = array(args.length);
        ret.addAll(Arrays.asList(args));
        return ret;
    }

    public static <T> List<T> array(int size) {
        return new ArrayList<T>(size);
    }

    public static <T> List<T> array() {
        return new ArrayList<T>();
    }

    public static <K, V> Map<K, V> newHashMap(int size) {
        return new HashMap<>(size);
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K> Set<K> newSet() {
        return new HashSet<K>();
    }

    public static <K> Set<K> newSet(int s) {
        return new HashSet<K>(s);
    }

    public static <T> boolean isEmpty(Collection<T> t) {
        return t == null || t.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> t) {
        return t == null || t.isEmpty();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty() || s.trim().isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static <F, T> List<T> map(List<F> from, Function<F, T> function) {
        if (isEmpty(from)) {
            return new ArrayList<>(0);
        }

        List<T> ret = array(from.size());
        for (F f : from) {
            ret.add(function.apply(f));
        }
        return ret;
    }

    /**
     * split a list to the num of list
     *
     * @param src
     * @param num
     * @param <T>
     * @return list
     */
    public static <T> List<List<T>> split(List<T> src, int num) {
        List<List<T>> result = new ArrayList<>();
        int size = src.size();
        int avg = size / num;
        int remainder = size % num;
        int start = 0;

        for (int i = 0; i < num; i++) {
            int end = start + avg + (remainder > 0 ? 1 : 0);
            result.add(new ArrayList<>(src.subList(start, Math.min(end, size))));
            start = end;
            remainder--;
        }

        return result;
    }

    public static <T> List<List<T>> splitBatch(List<T> src, int batchSize) {
        List<List<T>> result = new ArrayList<>();
        if (src == null || batchSize <= 0) {
            return result;
        }

        int totalSize = src.size();
        int fromIndex = 0;
        while (fromIndex < totalSize) {
            int toIndex = Math.min(fromIndex + batchSize, totalSize);
            result.add(new ArrayList<>(src.subList(fromIndex, toIndex)));
            fromIndex += batchSize;
        }

        return result;
    }



    public static int hash(String name) {
        String s = "" + Objects.hashCode(name);
        int hc = Objects.hashCode(s);
        if (hc > 0) {
            return hc;
        }
        return -hc;
    }

    public static int rand(int bound) {
        return random.nextInt(bound);
    }

}
