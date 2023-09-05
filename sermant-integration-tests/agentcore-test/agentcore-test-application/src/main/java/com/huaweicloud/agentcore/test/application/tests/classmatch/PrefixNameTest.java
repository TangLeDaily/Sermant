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

package com.huaweicloud.agentcore.test.application.tests.classmatch;

import com.huaweicloud.agentcore.test.application.results.ClassMatchResults;

/**
 * 测试通过前缀匹配类
 *
 * @author luanwenfei
 * @since 2023-09-08
 */
public class PrefixNameTest {
    private PrefixNameTest() {
    }

    /**
     * 测试静态方法
     *
     * @param enhanceFlag 回执标识
     */
    public static void staticFunction(boolean enhanceFlag) {
        if (enhanceFlag) {
            ClassMatchResults.MATCHER_CLASS_BY_CLASS_NAME_PREFIX.setResult(true);
        }
    }
}
