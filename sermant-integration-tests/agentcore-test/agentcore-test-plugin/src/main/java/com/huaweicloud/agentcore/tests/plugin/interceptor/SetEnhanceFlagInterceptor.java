package com.huaweicloud.agentcore.tests.plugin.interceptor;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;

/**
 * 告知应用被成功增强
 *
 * @author luanwenfei
 * @since 2022-10-24
 */
public class SetEnhanceFlagInterceptor extends AbstractInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) throws Exception {
        context.getArguments()[0] = true;
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) throws Exception {
        return context;
    }
}
