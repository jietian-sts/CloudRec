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
package com.alipay.application.service.system;

import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.system.TenantVO;
import com.alipay.application.share.vo.system.UserVO;
import com.alipay.common.constant.TenantConstants;
import com.alipay.common.exception.BizException;
import com.alipay.common.exception.TenantEditException;
import com.alipay.dao.dto.TenantDTO;
import com.alipay.dao.mapper.TenantMapper;
import com.alipay.dao.mapper.UserMapper;
import com.alipay.dao.po.TenantPO;
import com.alipay.dao.po.UserPO;
import com.alipay.application.service.system.domain.Tenant;
import com.alipay.application.service.system.domain.User;
import com.alipay.application.service.system.domain.enums.Status;
import com.alipay.application.service.system.domain.repo.TenantRepository;
import com.alipay.application.service.system.domain.repo.UserRepository;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 *@title TenantServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/6/13 17:44
 */

@Service
public class TenantServiceImpl implements TenantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Resource
    private TenantRepository tenantRepository;

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserMapper userMapper;

    @Override
    public ListVO<TenantVO> findList(TenantDTO tenantDTO) {
        ListVO<TenantVO> listVO = new ListVO<>();
        int count = tenantMapper.findCount(tenantDTO);
        if (count == 0) {
            return listVO;
        }

        if (tenantDTO.getPageLimit() !=null && tenantDTO.getPageLimit()) {
            tenantDTO.setOffset();
            List<TenantPO> list = tenantMapper.findList(tenantDTO);
            List<TenantVO> collect = list.stream().map(TenantVO::toVO).sorted(tenantComparator()).collect(Collectors.toList());
            listVO.setTotal(count);
            listVO.setData(collect);
        } else {
            List<TenantPO> list = tenantMapper.findList(tenantDTO);
            List<TenantVO> collect = list.stream().map(TenantVO::toVO)
                    .filter(item -> !item.getTenantName().equals(TenantConstants.GLOBAL_TENANT))
                    .sorted(tenantComparator())
                    .collect(Collectors.toList());
            listVO.setTotal(count);
            listVO.setData(collect);
        }
        return listVO;
    }

    @Override
    public ListVO<TenantVO> findAll() {
        ListVO<TenantVO> listVO = new ListVO<>();
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setPageLimit(Boolean.FALSE);

        int count = tenantMapper.findCount(tenantDTO);
        if (count == 0) {
            return listVO;
        }

        List<TenantPO> list = tenantMapper.findList(tenantDTO);
        List<TenantVO> collect = list.stream().map(TenantVO::toVO)
                .sorted(tenantComparator())
                .collect(Collectors.toList());
        listVO.setTotal(count);
        listVO.setData(collect);
        return listVO;
    }

    @Override
    public void saveTenant(Tenant tenant) {
        if (Objects.equals(tenant.getTenantName(), TenantConstants.DEFAULT_TENANT)) {
            throw new TenantEditException();
        }
        if (Objects.equals(tenant.getTenantName(), TenantConstants.GLOBAL_TENANT)) {
            throw new TenantEditException();
        }

        tenantRepository.save(tenant);
    }

    @Override
    public ListVO<UserVO> queryMemberList(TenantDTO tenantDTO) {
        ListVO<UserVO> listVO = new ListVO<>();

        int count = tenantRepository.memberCount(tenantDTO.getId());
        if (count == 0) {
            return listVO;
        }

        tenantDTO.setOffset();
        List<UserPO> list = userMapper.findMemberList(tenantDTO);
        List<UserVO> collect = list.stream().map(UserVO::toVo).toList();
        listVO.setTotal(count);
        listVO.setData(collect);
        return listVO;
    }

    @Override
    public void joinUser(String userId, Long tenantId) {
        User user = userRepository.find(userId);
        if (user == null) {
            throw new BizException("User does not exist:" + userId);
        }

        tenantRepository.join(user.getId(), tenantId);
    }

    @Override
    public ApiResponse<String> removeUser(Long uid, Long tenantId) {
        tenantRepository.remove(uid, tenantId);
        return ApiResponse.SUCCESS;
    }

    @Override
    public ApiResponse<String> changeTenant(String userId, Long tenantId) {
        User user = userRepository.find(userId);
        if (user == null) {
            return new ApiResponse<>(ApiResponse.FAIL_CODE, "User does not exist:" + userId);
        }

        Tenant tenant = tenantRepository.find(tenantId);
        if (tenant == null) {
            return new ApiResponse<>(ApiResponse.FAIL_CODE, "Tenant does not exist:" + tenantId);
        }

        int count = tenantRepository.exist(userId, tenantId);
        if (count == 0) {
            return new ApiResponse<>(ApiResponse.FAIL_CODE, "The user has not joined the tenant:" + tenantId);
        }

        if (!tenantId.equals(user.getTenantId())) {
            user.setTenantId(tenantId);
            userRepository.save(user);
        }

        return ApiResponse.SUCCESS;
    }

    private static Comparator<TenantVO> tenantComparator() {
        return Comparator.comparingInt(t -> {
            String tenantName = t.getTenantName();
            if (TenantConstants.GLOBAL_TENANT.equals(tenantName)) {
                return 0;
            } else if (TenantConstants.DEFAULT_TENANT.equals(tenantName)) {
                return 1;
            } else {
                return 2;
            }
        });
    }

    @Override
    public ApiResponse<List<TenantVO>> listAddedTenants(String userId) {
        User user = userRepository.find(userId);
        if (user == null) {
            return new ApiResponse<>(ApiResponse.FAIL_CODE, "User does not exist:" + userId);
        }

        List<TenantPO> list = tenantMapper.findListByUserId(userId);
        List<TenantVO> collect = list.stream()
                .map(TenantVO::toVO)
                .sorted(tenantComparator())
                .collect(Collectors.toList());
        return new ApiResponse<>(collect);
    }

    @Override
    public void joinDefaultTenant(String userId) {
        // Determine whether the default tenant exists, and creates it if it does not exist.
        Tenant tenant = tenantRepository.find(TenantConstants.DEFAULT_TENANT);
        if (tenant == null) {
            tenant = new Tenant(null, TenantConstants.DEFAULT_TENANT, Status.valid, TenantConstants.DEFAULT_TENANT_DESC);
            tenantRepository.save(tenant);
        }

        // join default tenant
        joinUser(userId, tenant.getId());

        // select default tenant
        User user = userRepository.find(userId);
        user.setTenantId(tenant.getId());
        userRepository.save(user);
    }

    @Override
    public List<Tenant> joinUserByTenants(String userId, String tenantIds) {

        User user = userRepository.find(userId);
        if (user == null) {
            throw new BizException("User does not exist:" + userId);
        }


        List<Tenant> tenantList = new ArrayList<>();
        //check before tenant&user
        if(StringUtils.isNotBlank(tenantIds)){
            //check before tenant_user
            List<TenantPO> tenantPOList = tenantMapper.findListByUserId(user.getUserId());
            for (TenantPO tenantPO : tenantPOList){
                tenantRepository.remove(Long.valueOf(user.getId()), tenantPO.getId());
            }

            for (String tenantIdStr : StringUtils.split(tenantIds, ",")) {
                Long tenantId = Long.valueOf(tenantIdStr);
                Tenant tenant = tenantRepository.find(tenantId);
                if(Objects.nonNull(tenant)){
                    tenantRepository.join(user.getId(), tenantId);
                    tenantList.add(tenant);
                }
            }
        }

        //若没有符合的租户，默认设置default租户
        if(CollectionUtils.isEmpty(tenantList)){
            joinDefaultTenant(userId);
            Tenant tenant = tenantRepository.find(TenantConstants.DEFAULT_TENANT);
            tenantList.add(tenant);
        }else {
            //更新user携带租户
            user.setTenantId(tenantList.get(0).getId());
            userRepository.save(user);
        }
        return tenantList;
    }
}
