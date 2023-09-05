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
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * 添加单一配置监听拦截器
 *
 * @author tangle
 * @since 2023-08-30
 */
public class AddConfigListenerInterceptor extends BaseInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) {
        String key = (String) context.getArguments()[PARAM_INDEX_1];
        String group = (String) context.getArguments()[PARAM_INDEX_2];
        context.getArguments()[PARAM_INDEX_0] = dynamicConfigService.doAddConfigListener(key, group,
                new DynamicConfigListener() {
                    @Override
                    public void process(DynamicConfigEvent event) {
                        // 监听成功回执
                        setListenerSuccess();
                    }

                    public void setListenerSuccess() {
                        try {
                            Class<?> targetClass = Class.forName(
                                    "com.huaweicloud.agentcore.test.application.tests.dynamicconfig"
                                            + ".DynamicConfigTest");
                            Method targetMethod = targetClass.getMethod("setListenerSuccess", boolean.class);
                            targetMethod.invoke(null, true);
                        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException
                                 | IllegalAccessException ignored) {
                            // ignore
                        }
                    }
                });
        LOGGER.log(Level.INFO, "[Agentcore-test-plugin]: AddConfigListener, key:{0}, group:{1}, result:{2}",
                new String[]{key, group, String.valueOf(context.getArguments()[PARAM_INDEX_0])});
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
