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
package com.alipay.application.service.rule.domain;


import com.alipay.dao.po.GlobalVariableConfigPO;

/*
 *@title GlobalVariable
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/12 10:58
 */
public class GlobalVariable {

    private String path;

    private String name;

    private String data;

    private String status;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static GlobalVariable toEntity(GlobalVariableConfigPO globalVariableConfigPO) {
        GlobalVariable globalVariable = new GlobalVariable();
        globalVariable.setPath(globalVariableConfigPO.getPath());
        globalVariable.setName(globalVariableConfigPO.getName());
        globalVariable.setData(globalVariableConfigPO.getData());
        globalVariable.setStatus(globalVariableConfigPO.getStatus());
        return globalVariable;
    }
}
