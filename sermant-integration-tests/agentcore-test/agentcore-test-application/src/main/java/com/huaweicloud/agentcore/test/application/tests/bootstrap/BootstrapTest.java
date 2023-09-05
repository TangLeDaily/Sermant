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

package com.huaweicloud.agentcore.test.application.tests.bootstrap;

import com.huaweicloud.agentcore.test.application.results.BootstrapResults;

import java.util.Map;

/**
 * 测试增强系统类
 *
 * @author tangle
 * @since 2023-09-08
 */
public class BootstrapTest {
    /**
     * 测试插件skip的结果值
     */
    private static final int COUNT = 999;

    /**
     * 测试插件修改的结果值
     */
    private static final String NAME = "modifyName";

    /**
     * 用于测试插件反射修改的回执结果：测试静态方法
     */
    private static boolean staticFlag;

    /**
     * 用于测试插件反射修改的回执结果：测试无参构造函数
     */
    private static boolean constructorFlag;

    /**
     * 测试系统类
     */
    public void testBootstrap() {
        staticFlag = false;
        constructorFlag = false;

        // 测试无参构造函数
        Thread thread = new Thread();
        BootstrapResults.ENHANCE_NO_ARGUMENT_CONSTRUCTOR.setResult(constructorFlag);

        // 测试静态方法
        Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
        BootstrapResults.ENHANCE_STATIC_FUNCTION.setResult(staticFlag);

        // 测试实例方法参数修改
        thread.setName("thread1");
        if (thread.getName().equals(NAME)) {
            BootstrapResults.ENHANCE_INSTANCE_FUNCTION.setResult(true);
        }

        // 测试静态方法skip
        if (Thread.activeCount() == COUNT) {
            BootstrapResults.SKIP_STATIC_FUNCTION.setResult(true);
        }
    }

    public static void setStaticFlag(boolean flag) {
        staticFlag = flag;
    }

    public static void setConstructorFlag(boolean flag) {
        constructorFlag = flag;
    }
}
