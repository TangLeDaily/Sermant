/*
 * Copyright (C) 2023-2023 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaweicloud.agentcore.test.application.results;

public enum MethodMatchResults {
    /**
     * Start:Test Method Matcher
     */
    MATCHER_CLASS_BY_CLASS_NAME_EXACTLY("Test matcher class by the exact class-name."),

    MATCHER_METHOD_BY_ANNOTATION("Test matcher method by single-annotation."),

    MATCHER_METHOD_BY_ANNOTATIONS("Test matcher method by multi-annotation."),

    MATCHER_METHOD_BY_METHOD_NAME_EXACTLY("Test matcher method by the exact method-name."),

    MATCHER_METHOD_BY_METHOD_NAME_PREFIX("Test matcher method by the method-name's prefix."),

    MATCHER_METHOD_BY_METHOD_NAME_INFIX("Test matcher method by the method-name's infix."),

    MATCHER_METHOD_BY_METHOD_NAME_SUFFIX("Test matcher method by the method-name's suffix."),

    MATCHER_CONSTRUCTOR("Test matcher constructor of class."),

    MATCHER_STATIC_METHODS("Test matcher static methods of class."),

    MATCHER_METHOD_BY_RETURN_TYPE("Test matcher method by return type."),

    MATCHER_METHOD_BY_ARGUMENTS_COUNT("Test matcher method by the count of arguments."),

    MATCHER_METHOD_BY_ARGUMENTS_TYPE("Test matcher method by the type of arguments.");

    /**
     * 用例描述
     */
    private String description;

    /**
     * 测试结果标识
     */
    private boolean result;

    /**
     * 构造函数
     *
     * @param description 用例描述
     */
    MethodMatchResults(String description) {
        this.description = description;
        this.result = false;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }
}
