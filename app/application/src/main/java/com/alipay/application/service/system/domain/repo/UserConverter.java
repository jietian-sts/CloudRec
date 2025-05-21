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
package com.alipay.application.service.system.domain.repo;


import com.alipay.application.service.system.domain.User;
import com.alipay.application.service.system.domain.enums.RoleNameType;
import com.alipay.application.service.system.domain.enums.Status;
import com.alipay.dao.converter.Converter;
import com.alipay.dao.po.UserPO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/*
 *@title UserBuilder
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/1/17 14:42
 */
@Component
public class UserConverter implements Converter<User, UserPO> {

    @Override
    public User toEntity(UserPO userPO) {
        User user = new User();
        BeanUtils.copyProperties(userPO, user);

        user.setStatus(Status.getStatus(userPO.getStatus()));
        user.setRoleName(RoleNameType.getRole(userPO.getRoleName()));
        return user;
    }

    @Override
    public UserPO toPo(User user) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(user, userPO);
        userPO.setStatus(user.getStatus().name());
        userPO.setRoleName(user.getRoleName().name());
        return userPO;
    }
}
