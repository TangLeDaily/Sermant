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

/**
 * 系统类测试用例结果
 *
 * @author tangle
 * @since 2023-09-08
 */
public class BootstrapResults {
    /**
     * 增强系统类无参构造方法
     */
    public static final TestCase ENHANCE_NO_ARGUMENT_CONSTRUCTOR = new TestCase("Test enhance system class no-argument "
            + "constructor.");

    /**
     * 增强系统类静态方法
     */
    public static final TestCase ENHANCE_STATIC_FUNCTION = new TestCase("Test enhance system class static function.");

    /**
     * 增强系统类实例方法
     */
    public static final TestCase ENHANCE_INSTANCE_FUNCTION = new TestCase("Test enhance system class instance "
            + "function.");

    /**
     * skip系统类静态方法
     */
    public static final TestCase SKIP_STATIC_FUNCTION = new TestCase("Test skip system class static "
            + "function.");

    private BootstrapResults() {
    }
}
