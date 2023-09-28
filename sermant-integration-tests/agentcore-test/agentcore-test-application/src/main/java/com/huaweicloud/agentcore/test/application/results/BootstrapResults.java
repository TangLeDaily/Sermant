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

public enum BootstrapResults {
    /**
     * Start:Test Class Matcher
     */
    ENHANCE_NO_ARGUMENT_CONSTRUCTOR("Test enhance system class no-argument constructor."),
    ENHANCE_STATIC_FUNCTION("Test enhance system class static function."),
    ENHANCE_INSTANCE_FUNCTION("Test enhance system class instance function."),
    ENHANCE_STATIC_FUNCTION_SKIP("Test skip system class static function.");

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
    BootstrapResults(String description) {
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
