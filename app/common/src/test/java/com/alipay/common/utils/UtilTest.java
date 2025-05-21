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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UtilTest {

    /**
     * [单测用例]测试场景：测试array方法，传入null
     */
    @Test
    public void testArrayWithNull() {
        List<String> result = Util.array((String[]) null);
        assertTrue(result.isEmpty());
    }

    /**
     * [单测用例]测试场景：测试array方法，传入空数组
     */
    @Test
    public void testArrayWithEmptyArray() {
        List<String> result = Util.array(new String[0]);
        assertTrue(result.isEmpty());
    }

    /**
     * [单测用例]测试场景：测试array方法，传入非空数组
     */
    @Test
    public void testArrayWithNonEmptyArray() {
        List<String> result = Util.array("a", "b", "c");
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("a", "b", "c")));
    }

    /**
     * [单测用例]测试场景：测试newHashMap方法，传入size
     */
    @Test
    public void testNewHashMapWithSize() {
        Map<String, Integer> result = Util.newHashMap(5);
        assertEquals(5, result.size());
    }

    /**
     * [单测用例]测试场景：测试newHashMap方法，不传入size
     */
    @Test
    public void testNewHashMapWithoutSize() {
        Map<String, Integer> result = Util.newHashMap();
        assertTrue(result.isEmpty());
    }

    /**
     * [单测用例]测试场景：测试newSet方法，不传入size
     */
    @Test
    public void testNewSetWithoutSize() {
        Set<String> result = Util.newSet();
        assertTrue(result.isEmpty());
    }

    /**
     * [单测用例]测试场景：测试isEmpty方法，传入null
     */
    @Test
    public void testIsEmptyWithNull() {
        assertTrue(Util.isEmpty((List<String>) null));
    }

    /**
     * [单测用例]测试场景：测试isEmpty方法，传入空集合
     */
    @Test
    public void testIsEmptyWithEmptyCollection() {
        assertTrue(Util.isEmpty(new ArrayList<>()));
    }

    /**
     * [单测用例]测试场景：测试isEmpty方法，传入非空集合
     */
    @Test
    public void testIsEmptyWithNonEmptyCollection() {
        assertFalse(Util.isEmpty(Arrays.asList("a", "b", "c")));
    }

    /**
     * [单测用例]测试场景：测试map方法，传入空集合
     */
    @Test
    public void testMapWithEmptyCollection() {
        List<String> result = Util.map(new ArrayList<>(), (Function<String, String>) s -> s.toUpperCase());
        assertTrue(result.isEmpty());
    }

    /**
     * [单测用例]测试场景：测试map方法，传入非空集合
     */
    @Test
    public void testMapWithNonEmptyCollection() {
        List<String> result = Util.map(Arrays.asList("a", "b", "c"), (Function<String, String>) s -> s.toUpperCase());
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("A", "B", "C")));
    }

    /**
     * [单测用例]测试场景：测试split方法，传入空集合
     */
    @Test
    public void testSplitWithEmptyCollection() {
        List<List<String>> result = Util.split(new ArrayList<>(), 2);
        assertTrue(result.isEmpty());
    }

    /**
     * [单测用例]测试场景：测试split方法，传入非空集合
     */
    @Test
    public void testSplitWithNonEmptyCollection() {
        List<List<String>> result = Util.split(Arrays.asList("a", "b", "c", "d"), 2);
        assertEquals(2, result.size());
        assertTrue(result.get(0).containsAll(Arrays.asList("a", "b")));
        assertTrue(result.get(1).containsAll(Arrays.asList("c", "d")));
    }

    /**
     * [单测用例]测试场景：测试splitBatch方法，传入空集合
     */
    @Test
    public void testSplitBatchWithEmptyCollection() {
        List<List<String>> result = Util.splitBatch(new ArrayList<>(), 2);
        assertTrue(result.isEmpty());
    }

    /**
     * [单测用例]测试场景：测试splitBatch方法，传入非空集合
     */
    @Test
    public void testSplitBatchWithNonEmptyCollection() {
        List<List<String>> result = Util.splitBatch(Arrays.asList("a", "b", "c", "d"), 2);
        assertEquals(2, result.size());
        assertTrue(result.get(0).containsAll(Arrays.asList("a", "b")));
        assertTrue(result.get(1).containsAll(Arrays.asList("c", "d")));
    }

    /**
     * [单测用例]测试场景：测试hash方法，传入空字符串
     */
    @Test
    public void testHashWithEmptyString() {
        int result = Util.hash("");
        assertEquals(48, result);
    }

    /**
     * [单测用例]测试场景：测试hash方法，传入非空字符串
     */
    @Test
    public void testHashWithNonEmptyString() {
        int result = Util.hash("test");
        assertTrue(result > 0);
    }

    /**
     * [单测用例]测试场景：测试rand方法
     */
    @Test
    public void testRand() {
        int result = Util.rand(10);
        assertTrue(result >= 0 && result < 10);
    }
}
