package com.huaweicloud.agentcore.tests.plugin.interceptor;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;

/**
 * 执行配置发布
 *
 * @author tangle
 * @since 2023-8-30
 */
public class RemoveGroupConfigListenerInterceptor extends BaseInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) {
        String group = (String) context.getArguments()[1];
        context.getArguments()[0] = dynamicConfigService.doRemoveGroupListener(group);
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
