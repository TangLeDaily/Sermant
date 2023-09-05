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
 * 类匹配测试用例结果
 *
 * @author tangle
 * @since 2023-09-08
 */
public class ClassMatchResults {
    /**
     * 单一注解类匹配
     */
    public static final TestCase MATCHER_CLASS_BY_ANNOTATION = new TestCase("Test matcher class by single-annotation.");

    /**
     * 多注解类匹配
     */
    public static final TestCase MATCHER_CLASS_BY_ANNOTATIONS = new TestCase("Test matcher class by multi-annotation.");

    /**
     * 类名称前缀匹配
     */
    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_PREFIX =
            new TestCase("Test matcher class by the class-name's prefix.");

    /**
     * 类名称中缀匹配
     */
    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_INFIX =
            new TestCase("Test matcher class by the class-name's infix.");

    /**
     * 类名称后缀匹配
     */
    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_SUFFIX =
            new TestCase("Test matcher class by the class-name's suffix.");

    /**
     * 单一超类匹配
     */
    public static final TestCase MATCHER_CLASS_BY_SUPER_TYPE = new TestCase("Test matcher class by single-superType.");

    /**
     * 多超类匹配
     */
    public static final TestCase MATCHER_CLASS_BY_SUPER_TYPES = new TestCase("Test matcher class by multi-superType.");

    private ClassMatchResults() {
    }
}
