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

public enum ClassMatchResults {
    /**
     * Start:Test Class Matcher
     */
    MATCHER_CLASS_BY_ANNOTATION("Test matcher class by single-annotation."),

    MATCHER_CLASS_BY_ANNOTATIONS("Test matcher class by multi-annotation."),

    MATCHER_CLASS_BY_CLASS_NAME_PREFIX("Test matcher class by the class-name's prefix."),

    MATCHER_CLASS_BY_CLASS_NAME_INFIX("Test matcher class by the class-name's infix."),

    MATCHER_CLASS_BY_CLASS_NAME_SUFFIX("Test matcher class by the class-name's suffix."),

    MATCHER_CLASS_BY_SUPER_TYPE("Test matcher class by single-superType."),

    MATCHER_CLASS_BY_SUPER_TYPES("Test matcher class by multi-superType.");

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
    ClassMatchResults(String description) {
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
