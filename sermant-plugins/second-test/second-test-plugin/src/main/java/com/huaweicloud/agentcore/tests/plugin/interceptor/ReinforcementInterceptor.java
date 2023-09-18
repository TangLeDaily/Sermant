package com.huaweicloud.agentcore.tests.plugin.interceptor;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;

import java.util.logging.Level;

public class ReinforcementInterceptor extends BaseInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) {
        LOGGER.log(Level.INFO, "ReinforcementInterceptor entry success.");
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
