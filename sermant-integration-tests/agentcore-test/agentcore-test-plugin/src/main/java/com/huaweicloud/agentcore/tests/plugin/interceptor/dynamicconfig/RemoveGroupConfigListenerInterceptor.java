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

package com.huaweicloud.agentcore.tests.plugin.interceptor.dynamicconfig;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;

import java.util.logging.Level;

/**
 * 移除组配置监听拦截器
 *
 * @author tangle
 * @since 2023-08-30
 */
public class RemoveGroupConfigListenerInterceptor extends BaseInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) {
        String group = (String) context.getArguments()[PARAM_INDEX_1];
        context.getArguments()[PARAM_INDEX_0] = dynamicConfigService.doRemoveGroupListener(group);
        LOGGER.log(Level.INFO, "[Agentcore-test-plugin]: RemoveGroupConfigListener, group:{0}, result:{1}",
                new String[]{group, String.valueOf(context.getArguments()[PARAM_INDEX_0])});
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
