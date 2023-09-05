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
 * 配置获取拦截器
 *
 * @author tangle
 * @since 2023-08-30
 */
public class GetConfigInterceptor extends BaseInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) {
        String key = (String) context.getArguments()[PARAM_INDEX_1];
        String group = (String) context.getArguments()[PARAM_INDEX_2];
        String predictContent = (String) context.getArguments()[PARAM_INDEX_3];
        String content = dynamicConfigService.doGetConfig(key, group).orElse("");
        context.getArguments()[PARAM_INDEX_0] = predictContent.equals(content);
        LOGGER.log(Level.INFO, "[Agentcore-test-plugin]: GetConfig, key:{0}, group:{1}, config:{2}, result:{3}",
                new String[]{key, group, content, String.valueOf(context.getArguments()[PARAM_INDEX_0])});
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
