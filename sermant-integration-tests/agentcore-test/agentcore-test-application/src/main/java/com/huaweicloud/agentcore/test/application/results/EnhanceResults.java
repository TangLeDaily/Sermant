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
 * 增强测试用例结果
 *
 * @author tangle
 * @since 2023-09-08
 */
public class EnhanceResults {
    /**
     * 增强修改成员属性
     */
    public static final TestCase MODIFY_MEMBER_FIELDS = new TestCase("Test modify the member fields of object.");

    /**
     * 增强修改静态属性
     */
    public static final TestCase MODIFY_STATIC_FIELDS = new TestCase("Test modify the static fields of object.");

    /**
     * 增强修改方法入参
     */
    public static final TestCase MODIFY_ARGUMENTS = new TestCase("Test modify the arguments of method.");

    /**
     * 增强修改方法返回值
     */
    public static final TestCase MODIFY_RESULT = new TestCase("Test modify the result of method.");

    /**
     * 增强跳过方法
     */
    public static final TestCase SKIP_METHOD = new TestCase("Test skip the method.");

    private EnhanceResults() {
    }
}
