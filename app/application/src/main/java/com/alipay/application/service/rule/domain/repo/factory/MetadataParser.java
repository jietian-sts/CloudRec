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
package com.alipay.application.service.rule.domain.repo.factory;


import com.alipay.common.enums.RiskLevel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/*
 *@title MetadataParser
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/18 16:00
 */
public class MetadataParser {
    /**
     * 远程仓库路径
     */
    public static final String RULE_REPO_URL = "https://github.com/antgroup/CloudRec.git";

    /**
     * 规则文件路径
     */
    public static final String RULE_FILE_NAME = "rules/";

    /**
     * 全局变量路径
     */
    public static final String GLOBAL_VARIABLE = "rules/data/";

    /**
     * 策略文件名称
     */
    public static final String REGO_FILE_NAME = "policy.rego";

    /**
     * 规则的元数据文件名称
     */
    public static final String METADATA_JSON_FILE_NAME = "metadata.json";

    /**
     * 保存规则相关的全局变量的path文件名称
     */
    public static final String RELATION_JSON_FILE_NAME = "relation.json";


    public static Metadata parseMetaDataJson(Path jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonFile.toFile(), Metadata.class);
    }

    public static Map<String, Object> parseGlobalVariableJson(Path jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonFile.toFile(), Map.class);
    }

    public static List<String> parseRelationJson(Path dir) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String content = new String(Files.readAllBytes(dir));
            return objectMapper.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            return null;
        }
    }


    public static boolean verifyData(Metadata metadata, String policy) {
        if (StringUtils.isEmpty(metadata.getPlatform()) || StringUtils.isEmpty(metadata.getResourceType())
                || StringUtils.isEmpty(metadata.getCode()) || StringUtils.isEmpty(metadata.getName())
                || !RiskLevel.exist(metadata.getLevel())) {
            return false;
        }

        if (StringUtils.isEmpty(policy)) {
            return false;
        }

        return true;
    }


    public static class Metadata {

        public Metadata() {
        }

        /**
         * {
         * "platform": "ALI_CLOUD",
         * "resourceType": "RAM User",
         * "name": "阿里云-RAM-RAM User拥有大权限但无调用来源ACL",
         * "code": "ali_cloud_ram_no_acl",
         * "description": "拥有 AliyunRAMFullAccess、AdministratorAccess权限的RAM User没有设置调用来源ACL",
         * "level": "high",
         * "category": "身份安全",
         * "advice": "设置访问控制ACL",
         * "link": "https://example.com/sql-injection-prevention"
         * }
         */

        private String platform;
        private String resourceType;
        private String name;
        private String code;
        private String description;
        private String level;
        private List<String> categoryList;
        private String advice;
        private String link;
        private String context;
        private String linkedDataList;

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }


        public String getAdvice() {
            return advice;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<String> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<String> categoryList) {
            this.categoryList = categoryList;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLinkedDataList() {
            return linkedDataList;
        }

        public void setLinkedDataList(String linkedDataList) {
            this.linkedDataList = linkedDataList;
        }

        @Override
        public String toString() {
            return "Metadata{" +
                    "platform='" + platform + '\'' +
                    ", resourceType='" + resourceType + '\'' +
                    ", name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    ", level='" + level + '\'' +
                    ", categoryList=" + categoryList +
                    ", advice='" + advice + '\'' +
                    ", link='" + link + '\'' +
                    ", context='" + context + '\'' +
                    ", linkedDataList='" + linkedDataList + '\'' +
                    '}';
        }
    }

}
