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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilTest {

    @Mock
    private Calendar calendar;

    /**
     * [单测用例]测试场景：测试正常情况
     */
    @Test
    public void testPlusDay_NormalCase() {
        Date date = new Date();
        int day = 1;
        Mockito.when(calendar.getTime()).thenReturn(date);
        Mockito.when(Calendar.getInstance()).thenReturn(calendar);
        Date result = DateUtil.plusDay(date, day);
        Mockito.verify(calendar).add(Calendar.DAY_OF_MONTH, day);
        assertNotNull(result);
    }

    /**
     * [单测用例]测试场景：测试日期为null的情况
     */
    @Test(expected = NullPointerException.class)
    public void testPlusDay_NullDate() {
        Date date = null;
        int day = 1;
        DateUtil.plusDay(date, day);
    }

    /**
     * [单测用例]测试场景：测试天数为负数的情况
     */
    @Test
    public void testPlusDay_NegativeDay() {
        Date date = new Date();
        int day = -1;
        Mockito.when(calendar.getTime()).thenReturn(date);
        Mockito.when(Calendar.getInstance()).thenReturn(calendar);
        Date result = DateUtil.plusDay(date, day);
        Mockito.verify(calendar).add(Calendar.DAY_OF_MONTH, day);
        assertNotNull(result);
    }
}
