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
package com.alipay.application.service.account;


import com.alibaba.fastjson.JSON;
import com.alipay.application.service.account.enums.SecurityProductStatus;
import com.alipay.application.service.account.enums.SecurityProductType;
import com.alipay.application.service.system.domain.enums.Status;
import com.alipay.application.share.request.account.GetCloudAccountSecurityProductPostureListRequest;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.account.CloudAccountSecurityProductPostureVO;
import com.alipay.application.share.vo.account.SecurityProductOverallPostureVO;
import com.alipay.dao.context.UserInfoContext;
import com.alipay.dao.dto.CloudAccountDTO;
import com.alipay.dao.dto.SecurityProductPostureDTO;
import com.alipay.dao.mapper.CloudAccountMapper;
import com.alipay.dao.mapper.PlatformMapper;
import com.alipay.dao.mapper.SecurityProductPostureMapper;
import com.alipay.dao.mapper.TenantMapper;
import com.alipay.dao.po.CloudAccountPO;
import com.alipay.dao.po.PlatformPO;
import com.alipay.dao.po.SecurityProductPosturePO;
import com.alipay.dao.po.TenantPO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/*
 *@title SecurityProductPostureServiceImpl
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/4/10 10:56
 */
@Service
@Slf4j
public class SecurityProductPostureServiceImpl implements SecurityProductPostureService {

    @Resource
    private SecurityProductPostureMapper securityProductPostureMapper;

    @Resource
    private CloudAccountMapper cloudAccountMapper;

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private PlatformMapper platformMapper;

    /**
     * 获取安全产品整体态势
     *
     * @param platform 平台
     * @return 安全产品整体态势
     */
    @Override
    public SecurityProductOverallPostureVO getOverallPosture(String platform) {
        // 获取云账号总数
        int count = cloudAccountMapper.findCount(CloudAccountDTO.builder()
                .platform(platform)
                .accountStatus(Status.valid.name())
                .tenantId(UserInfoContext.getCurrentUser().getTenantId())
                .build());

        SecurityProductOverallPostureVO securityProductOverallPostureVO = new SecurityProductOverallPostureVO();
        securityProductOverallPostureVO.setCloudAccountCount(count);

        List<SecurityProductOverallPostureVO.SecurityProductOverall> securityProductOverallList = new ArrayList<>();
        List<String> securityProductList = SecurityProductType.getSecurityProductList(platform);
        for (String securityProductType : securityProductList) {
            SecurityProductOverallPostureVO.SecurityProductOverall securityProductOverall = new SecurityProductOverallPostureVO.SecurityProductOverall();
            securityProductOverall.setProductType(securityProductType);
            Map<String, Integer> securityProductCount = getSecurityProductCount(platform, securityProductType);
            securityProductOverall.setProtectedCount(securityProductCount.get(SecurityProductStatus.open.name()));
            securityProductOverall.setUnprotectedCount(securityProductCount.get(SecurityProductStatus.close.name()));
            securityProductOverallList.add(securityProductOverall);
        }

        securityProductOverallPostureVO.setSecurityProductOverallList(securityProductOverallList);

        return securityProductOverallPostureVO;
    }

    private Map<String, Integer> getSecurityProductCount(String platform, String securityProductType) {
        SecurityProductPostureDTO dto = SecurityProductPostureDTO.builder()
                .productType(securityProductType)
                .platform(platform)
                .tenantId(UserInfoContext.getCurrentUser().getTenantId())
                .build();
        List<SecurityProductPosturePO> list = securityProductPostureMapper.findList(dto);
        long openCount = list.stream().filter(e -> SecurityProductStatus.open.name().equals(e.getStatus())).count();
        long notOpenCount = list.stream().filter(e -> SecurityProductStatus.close.name().equals(e.getStatus())).count();
        Map<String, Integer> result = new HashMap<>();
        result.put(SecurityProductStatus.open.name(), (int) openCount);
        result.put(SecurityProductStatus.close.name(), (int) notOpenCount);
        return result;
    }


    /**
     * 获取云账号安全产品态势列表
     *
     * @param request req
     * @return 云账号安全产品态势列表
     */
    @Override
    public ListVO<CloudAccountSecurityProductPostureVO> getCloudAccountSecurityProductPostureList(GetCloudAccountSecurityProductPostureListRequest request) {
        log.info("getCloudAccountSecurityProductPostureList request: {}", JSON.toJSONString(request));
        ListVO<CloudAccountSecurityProductPostureVO> listVO = new ListVO<>();
        SecurityProductPostureDTO dto = SecurityProductPostureDTO.builder()
                .platform(request.getPlatform())
                .cloudAccountId(request.getCloudAccountId())
                .tenantId(UserInfoContext.getCurrentUser().getTenantId())
                .build();

        List<SecurityProductPosturePO> orgList = securityProductPostureMapper.findList(dto);

        // 内存分页
        List<SecurityProductPosturePO> filterList;
        if (request.getStatusMap() == null || request.getStatusMap().isEmpty()) {
            filterList = orgList;
        } else {
            filterList = orgList.stream().filter(e -> {
                        String status = request.getStatusMap().get(e.getProductType());
                        if (status == null) {
                            return true;
                        }
                        return status.equals(e.getStatus());
                    }
            ).toList();
        }

        List<String> securityProductList = SecurityProductType.getSecurityProductList(request.getPlatform());
        int productCount = securityProductList.size();
        List<Map.Entry<String, List<SecurityProductPosturePO>>> pageList = filterList.parallelStream()
                .collect(Collectors.groupingBy(SecurityProductPosturePO::getCloudAccountId, Collectors.toList()))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() == productCount) // 过滤条件：value对应的list的大小必须等于4
                .sorted(Map.Entry.comparingByKey())
                .toList();

        int count = pageList.size();
        pageList = pageList.stream().skip((long) (request.getPage() - 1) * request.getSize()).limit(request.getSize()).toList();

        List<CloudAccountSecurityProductPostureVO> result = pageList.stream().map(e -> {
            List<SecurityProductPosturePO> list = e.getValue();
            SecurityProductPosturePO securityProductPosturePO = list.get(0);
            CloudAccountSecurityProductPostureVO cloudAccountSecurityProductPostureVO = new CloudAccountSecurityProductPostureVO();

            // 基础信息
            cloudAccountSecurityProductPostureVO.setGmtModified(securityProductPosturePO.getGmtModified());
            cloudAccountSecurityProductPostureVO.setPlatform(securityProductPosturePO.getPlatform());
            cloudAccountSecurityProductPostureVO.setCloudAccountId(securityProductPosturePO.getCloudAccountId());
            CloudAccountPO cloudAccountPO = cloudAccountMapper.findByCloudAccountId(securityProductPosturePO.getCloudAccountId());
            if (cloudAccountPO != null) {
                cloudAccountSecurityProductPostureVO.setAlias(cloudAccountPO.getAlias());
            }

            // 资产数量
            int total = list.stream().map(SecurityProductPosturePO::getTotal).filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
            cloudAccountSecurityProductPostureVO.setTotal(total);

            //归属租户
            TenantPO tenantPO = tenantMapper.selectByPrimaryKey(securityProductPosturePO.getTenantId());
            if (tenantPO != null) {
                cloudAccountSecurityProductPostureVO.setTenantName(tenantPO.getTenantName());
            }

            List<CloudAccountSecurityProductPostureVO.ProductPosture> productPostureList = getProductPostures(list);
            cloudAccountSecurityProductPostureVO.setProductPostureList(productPostureList);
            return cloudAccountSecurityProductPostureVO;

        }).toList();


        listVO.setData(result);
        listVO.setTotal(count);
        return listVO;
    }

    private static @NotNull List<CloudAccountSecurityProductPostureVO.ProductPosture> getProductPostures(List<SecurityProductPosturePO> list) {
        List<CloudAccountSecurityProductPostureVO.ProductPosture> productPostureList = new ArrayList<>();
        for (SecurityProductPosturePO po : list) {
            CloudAccountSecurityProductPostureVO.ProductPosture productPosture =
                    new CloudAccountSecurityProductPostureVO.ProductPosture(
                            po.getProductType(),
                            po.getVersionDesc(),
                            po.getPolicy(),
                            po.getPolicyDetail() == null ? "{}" : po.getPolicyDetail(),
                            po.getStatus(),
                            po.getProtectedCount(), po.getTotal()
                    );
            productPostureList.add(productPosture);
        }
        return productPostureList;
    }

    /**
     * 开启云产品防护
     *
     * @param platform       平台
     * @param cloudAccountId 云账号id
     * @param productType    产品类型
     * @return 开通情况
     */
    @Override
    public String openSecurityProduct(String platform, String cloudAccountId, String productType) {
        return "暂不支持此功能";
    }

    /**
     * 获取支持安全管控的云平台列表
     *
     * @return 支持安全管控的云平台列表
     */
    @Override
    public List<PlatformPO> getPlatformList() {
        List<String> supportedPlatformList = SecurityProductType.getSupportedPlatformList();
        List<PlatformPO> res = new ArrayList<>();
        for (String platform : supportedPlatformList) {
            PlatformPO platformPO = platformMapper.findByPlatform(platform);
            if (platformPO != null) {
                res.add(platformPO);
            }
        }

        return res;
    }
}
