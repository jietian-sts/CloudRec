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
package com.alipay.application.share.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ApiResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    public static final int ACCESS_DENIED = 401;

    public static final ApiResponse<String> SUCCESS = new ApiResponse<>("success");
    public static final ApiResponse<String> FAIL = new ApiResponse<>(FAIL_CODE, "error");

    private int code;
    private String errorCode;
    private String errorMsg;
    private String msg;
    private T content;

    public ApiResponse() {
    }

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResponse(int code, T content) {
        this.code = code;
        this.content = content;
    }

    public ApiResponse(String msg) {
        this.code = SUCCESS_CODE;
        this.msg = msg;
    }

    public ApiResponse(T content) {
        this.code = SUCCESS_CODE;
        this.msg = "success";
        this.content = content;
    }

    public ApiResponse(int code, String errorCode, String errorMsg, String msg) {
        this.code = code;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.msg = msg;
    }

    public ApiResponse(int code, T content, String msg) {
        this.code = code;
        this.content = content;
        this.msg = msg;
    }

    public ApiResponse(Errors error) {
        this.code = FAIL_CODE;
        List<ObjectError> allErrors = error.getAllErrors();
        if (!allErrors.isEmpty()) {
            // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
            FieldError fieldError = (FieldError) allErrors.get(0);
            this.msg = fieldError.getDefaultMessage();
        }
    }

    @Override
    public String toString() {
        return "ReturnT [code=" + code + ", msg=" + msg + ", content=" + content + "]";
    }

}
