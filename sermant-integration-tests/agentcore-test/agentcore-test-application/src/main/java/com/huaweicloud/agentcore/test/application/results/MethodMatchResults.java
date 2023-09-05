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
 * 方法匹配测试用例结果
 *
 * @author tangle
 * @since 2023-09-08
 */
public class MethodMatchResults {
    /**
     * 精确类名匹配
     */
    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_EXACTLY =
            new TestCase("Test matcher class by the exact class-name.");

    /**
     * 单一注解方法匹配
     */
    public static final TestCase MATCHER_METHOD_BY_ANNOTATION =
            new TestCase("Test matcher method by single-annotation.");

    /**
     * 多注解方法匹配
     */
    public static final TestCase MATCHER_METHOD_BY_ANNOTATIONS =
            new TestCase("Test matcher method by multi-annotation.");

    /**
     * 精确方法名匹配
     */
    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_EXACTLY =
            new TestCase("Test matcher method by the exact method-name.");

    /**
     * 前缀方法名匹配
     */
    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_PREFIX =
            new TestCase("Test matcher method by the method-name's prefix.");

    /**
     * 中缀方法名匹配
     */
    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_INFIX =
            new TestCase("Test matcher method by the method-name's infix.");

    /**
     * 后缀方法名匹配
     */
    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_SUFFIX =
            new TestCase("Test matcher method by the method-name's suffix.");

    /**
     * 类的构造方法匹配
     */
    public static final TestCase MATCHER_CONSTRUCTOR = new TestCase("Test matcher constructor of class.");

    /**
     * 静态方法匹配
     */
    public static final TestCase MATCHER_STATIC_METHODS = new TestCase("Test matcher static methods of class.");

    /**
     * 根据返回类型进行方法匹配
     */
    public static final TestCase MATCHER_METHOD_BY_RETURN_TYPE = new TestCase("Test matcher method by return type.");

    /**
     * 根据参数数量进行方法匹配
     */
    public static final TestCase MATCHER_METHOD_BY_ARGUMENTS_COUNT =
            new TestCase("Test matcher method by the count of arguments.");

    /**
     * 根据参数类型进行方法匹配
     */
    public static final TestCase MATCHER_METHOD_BY_ARGUMENTS_TYPE =
            new TestCase("Test matcher method by the type of arguments.");

    private MethodMatchResults() {
    }
}
