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

package com.huaweicloud.agentcore.test.application.controller;

import com.huaweicloud.agentcore.test.application.results.BootstrapResults;
import com.huaweicloud.agentcore.test.application.results.ClassMatchResults;
import com.huaweicloud.agentcore.test.application.results.DynamicConfigResults;
import com.huaweicloud.agentcore.test.application.results.EnhanceResults;
import com.huaweicloud.agentcore.test.application.results.MethodMatchResults;
import com.huaweicloud.agentcore.test.application.results.TestCase;
import com.huaweicloud.agentcore.test.application.tests.bootstrap.BootstrapTest;
import com.huaweicloud.agentcore.test.application.tests.classmatch.ClassMatchersTest;
import com.huaweicloud.agentcore.test.application.tests.dynamicconfig.DynamicConfigTest;
import com.huaweicloud.agentcore.test.application.tests.enhancement.EnhancementTest;
import com.huaweicloud.agentcore.test.application.tests.methodmatch.MethodMatchersTest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * http接口方法
 *
 * @author tangle
 * @since 2023-09-08
 */
public class TestController {
    /**
     * 作为服务运行成功的请求判断接口
     *
     * @return “OK”
     */
    public String ping() {
        return "OK";
    }

    /**
     * 测试类匹配
     *
     * @return 测试结果
     */
    public String testClassMatch() {
        Map<String, Boolean> classMatchResult = new HashMap<>();
        try {
            ClassMatchersTest classMatchersTest = new ClassMatchersTest();
            classMatchersTest.testClassMatchers();

            for (Field field : ClassMatchResults.class.getFields()) {
                TestCase testCase = (TestCase) field.get(null);
                classMatchResult.put(testCase.getDescription(), testCase.isResult());
            }
        } catch (IllegalAccessException exception) {
            classMatchResult.put(buildExceptionKey(exception), false);
        }
        return convertMapToString(classMatchResult);
    }

    /**
     * 测试方法匹配
     *
     * @return 测试结果
     */
    public String testMethodMatch() {
        Map<String, Boolean> methodMatchResult = new HashMap<>();
        try {
            MethodMatchersTest methodMatchersTest = new MethodMatchersTest(false);
            methodMatchersTest.testMethodMatchers();

            for (Field field : MethodMatchResults.class.getFields()) {
                TestCase testCase = (TestCase) field.get(null);
                methodMatchResult.put(testCase.getDescription(), testCase.isResult());
            }
        } catch (IllegalAccessException exception) {
            methodMatchResult.put(buildExceptionKey(exception), false);
        }
        return convertMapToString(methodMatchResult);
    }

    /**
     * 测试增强
     *
     * @return 测试结果
     */
    public String testEnhancement() {
        Map<String, Boolean> enhancementResult = new HashMap<>();
        try {
            EnhancementTest enhancementTest = new EnhancementTest();
            enhancementTest.testEnhancement();

            for (Field field : EnhanceResults.class.getFields()) {
                TestCase testCase = (TestCase) field.get(null);
                enhancementResult.put(testCase.getDescription(), testCase.isResult());
            }
        } catch (IllegalAccessException exception) {
            enhancementResult.put(buildExceptionKey(exception), false);
        }
        return convertMapToString(enhancementResult);
    }

    /**
     * 测试动态配置
     *
     * @return 测试结果
     */
    public String testDynamicConfig() {
        Map<String, Boolean> dynamicConfigResult = new HashMap<>();
        try {
            DynamicConfigTest dynamicConfigTest = new DynamicConfigTest();
            dynamicConfigTest.testDynamicConfig();

            for (Field field : DynamicConfigResults.class.getFields()) {
                TestCase testCase = (TestCase) field.get(null);
                dynamicConfigResult.put(testCase.getDescription(), testCase.isResult());
            }
        } catch (IllegalAccessException | InterruptedException exception) {
            dynamicConfigResult.put(buildExceptionKey(exception), false);
        }
        return convertMapToString(dynamicConfigResult);
    }

    /**
     * 测试系统类
     *
     * @return 测试结果
     */
    public String testBootstrap() {
        Map<String, Boolean> bootstrapResult = new HashMap<>();
        try {
            BootstrapTest bootstrapTest = new BootstrapTest();
            bootstrapTest.testBootstrap();

            for (Field field : BootstrapResults.class.getFields()) {
                TestCase testCase = (TestCase) field.get(null);
                bootstrapResult.put(testCase.getDescription(), testCase.isResult());
            }
        } catch (IllegalAccessException exception) {
            bootstrapResult.put(buildExceptionKey(exception), false);
        }
        return convertMapToString(bootstrapResult);
    }

    private String buildExceptionKey(Exception e) {
        return "Unexpected exception occurs: " + e.getMessage();
    }

    /**
     * map转成JSON的string格式
     *
     * @return string结果
     */
    private String convertMapToString(Map<String, Boolean> map) {
        StringBuilder stb = new StringBuilder();
        stb.append("{");
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            stb.append("\"").append(entry.getKey()).append("\"");
            stb.append(":");
            stb.append(entry.getValue()).append(",");
        }
        stb.deleteCharAt(stb.length() - 1);
        stb.append("}");
        return stb.toString();
    }
}
