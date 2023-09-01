package com.huaweicloud.agentcore.tests.plugin.interceptor;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;
import com.huaweicloud.sermant.core.service.ServiceManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.DynamicConfigService;

/**
 * 执行配置发布
 *
 * @author tangle
 * @since 2023-8-30
 */
public class GetConfigInterceptor extends BaseInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) {
        String key = (String) context.getArguments()[1];
        String group = (String) context.getArguments()[2];
        String predictContent = (String) context.getArguments()[3];
        String content = dynamicConfigService.doGetConfig(key, group).orElse("");
        context.getArguments()[0] = predictContent.equals(content);
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
