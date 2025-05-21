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
package com.alipay.application.service.rule;

import com.alipay.application.share.vo.ApiResponse;
import com.alipay.application.share.vo.ListVO;
import com.alipay.application.share.vo.rule.GlobalVariableConfigVO;
import com.alipay.common.enums.Status;
import com.alipay.common.exception.BizException;
import com.alipay.dao.dto.GlobalVariableConfigDTO;
import com.alipay.dao.mapper.GlobalVariableConfigMapper;
import com.alipay.dao.mapper.UserMapper;
import com.alipay.dao.po.GlobalVariableConfigPO;
import com.alipay.dao.po.RulePO;
import com.alipay.dao.po.UserPO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
 *@title GlobalVariableConfigService
 *@description
 *@author jietian
 *@version 1.0
 *@create 2024/10/21 11:47
 */
@Service
public class GlobalVariableConfigServiceImpl implements GlobalVariableConfigService {

    @Resource
    private GlobalVariableConfigMapper globalVariableConfigMapper;

    @Resource
    private UserMapper userMapper;

    private boolean isValidJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * path 后续会导出作为文件名称，因此需要去除特殊字符
     *
     * @param path 用户输入的path
     * @return 转换后的安全的path
     */
    private String sanitizePath(String path) {
        return path
                .replace("/", "_")
                .replace("..", "")
                // Filter illegal characters (Universal for Windows/Linux)
                .replaceAll("[\\\\:*?\"<>|]", "");
    }

    @Override
    public void saveGlobalVariableConfig(GlobalVariableConfigDTO globalVariableConfigDTO) {
        if (!isValidJson(globalVariableConfigDTO.getData())) {
            throw new RuntimeException("Variable value is not a legal JSON string");
        }

        UserPO userPO = userMapper.findOne(globalVariableConfigDTO.getUserId());
        if (userPO == null) {
            throw new BizException("User does not exist");
        }

        if (globalVariableConfigDTO.getId() == null) {
            GlobalVariableConfigPO exist = globalVariableConfigMapper.findByPath(globalVariableConfigDTO.getPath());
            if (exist != null) {
                throw new BizException("The path already exists");
            }

            GlobalVariableConfigPO globalVariableConfigPO = new GlobalVariableConfigPO();
            globalVariableConfigPO.setData(globalVariableConfigDTO.getData());
            globalVariableConfigPO.setName(globalVariableConfigDTO.getName());
            globalVariableConfigPO.setPath(sanitizePath(globalVariableConfigDTO.getPath()));
            globalVariableConfigPO.setStatus(Status.valid.name());
            globalVariableConfigPO.setUserId(globalVariableConfigDTO.getUserId());
            globalVariableConfigPO.setUsername(userPO.getUsername());

            globalVariableConfigMapper.insertSelective(globalVariableConfigPO);
        } else {
            GlobalVariableConfigPO globalVariableConfigPO = globalVariableConfigMapper
                    .selectByPrimaryKey(globalVariableConfigDTO.getId());
            globalVariableConfigPO.setData(globalVariableConfigDTO.getData());
            globalVariableConfigPO.setName(globalVariableConfigDTO.getName());
            globalVariableConfigPO.setStatus(globalVariableConfigDTO.getStatus());
            globalVariableConfigPO.setUserId(globalVariableConfigDTO.getUserId());
            globalVariableConfigPO.setUsername(userPO.getUsername());
            globalVariableConfigPO.setGmtModified(new Date());
            globalVariableConfigMapper.updateByPrimaryKeySelective(globalVariableConfigPO);
        }
    }

    @Override
    public void deleteGlobalVariableConfig(Long id) {
        GlobalVariableConfigPO globalVariableConfigPO = globalVariableConfigMapper.selectByPrimaryKey(id);
        if (globalVariableConfigPO == null) {
            throw new BizException("The configuration does not exist");
        }

        List<RulePO> rulePOList = globalVariableConfigMapper.findRelRuleList(id);
        if (!rulePOList.isEmpty()) {
            throw new RuntimeException("Configure bound rules, which cannot be deleted for the time being. You can delete them after unbinding the rules.");
        }

        globalVariableConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ApiResponse<ListVO<GlobalVariableConfigVO>> listGlobalVariableConfig(
            GlobalVariableConfigDTO globalVariableConfigDTO) {
        ListVO<GlobalVariableConfigVO> listVO = new ListVO<>();

        int count = globalVariableConfigMapper.findCount(globalVariableConfigDTO);
        if (count == 0) {
            return new ApiResponse<>(listVO);
        }

        globalVariableConfigDTO.setOffset();
        List<GlobalVariableConfigPO> globalVariableConfigPOS = globalVariableConfigMapper.findList(globalVariableConfigDTO);

        List<GlobalVariableConfigVO> list = globalVariableConfigPOS.stream()
                .map(GlobalVariableConfigVO::build)
                .toList();
        listVO.setData(list);
        listVO.setTotal(count);
        return new ApiResponse<>(listVO);
    }
}
