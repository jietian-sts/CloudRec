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
package com.alipay.dao.mapper;

import com.alipay.dao.po.RuleRegoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RuleRegoMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(RuleRegoPO record);

    RuleRegoPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuleRegoPO record);

    RuleRegoPO findLatestOne(Long ruleId);

    List<RuleRegoPO> findList(@Param("ruleId") Long ruleId, @Param("size") Integer size,
                              @Param("offset") Integer offset);

    int findCount(Long ruleId);
}