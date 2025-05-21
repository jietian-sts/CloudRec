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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ListUtilsTest {

    //[单测用例]测试场景：测试输入为null的情况
    @Test
    public void testSetListWithNullInput() {
        List<List<String>> input = null;
        List<String> result = ListUtils.setList(input);
        assertNull(result);
    }

    //[单测用例]测试场景：测试输入为空列表的情况
    @Test
    public void testSetListWithEmptyInput() {
        List<List<String>> input = Collections.emptyList();
        List<String> result = ListUtils.setList(input);
        assertNull(result);
    }

    //[单测用例]测试场景：测试输入列表中包含空列表的情况
    @Test
    public void testSetListWithEmptySubList() {
        List<List<String>> input = Arrays.asList(Collections.emptyList(), Arrays.asList("1", "2"));
        List<String> result = ListUtils.setList(input);
        assertEquals(1, result.size());
        assertEquals("2", result.get(0));
    }

    //[单测用例]测试场景：测试输入列表中包含单元素列表的情况
    @Test
    public void testSetListWithSingleElementSubList() {
        List<List<String>> input = Arrays.asList(Arrays.asList("1"), Arrays.asList("2", "3"));
        List<String> result = ListUtils.setList(input);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0));
        assertEquals("3", result.get(1));
    }

    //[单测用例]测试场景：测试输入列表中包含多元素列表的情况
    @Test
    public void testSetListWithMultiElementSubList() {
        List<List<String>> input = Arrays.asList(Arrays.asList("1", "2"), Arrays.asList("3", "4"));
        List<String> result = ListUtils.setList(input);
        assertEquals(2, result.size());
        assertEquals("2", result.get(0));
        assertEquals("4", result.get(1));
    }
}
