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
package com.alipay.dao.dto;

public class PageDTO {

    private Integer page;

    private Integer size;

    private Integer offset;

    /**
     * true:分页 false:不分页
     */
    private Boolean pageLimit;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset() {
        if (this.page != null && this.size != null) {
            this.offset = (this.page - 1) * this.size;
        } else {
            this.page = 1;
            this.size = 10;
            this.offset = 0;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Boolean getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(Boolean pageLimit) {
        this.pageLimit = pageLimit;
    }
}
