package com.huaweicloud.dynamic.test.first.plugin.interceptor;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RepeatEnhanceInterceptor extends AbstractInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    @Override
    public ExecuteContext before(ExecuteContext context){
        context.getArguments()[0] = true;
        LOGGER.log(Level.INFO, "Test repeat enhance, first plugin enhance success");
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context){
        return context;
    }
}
