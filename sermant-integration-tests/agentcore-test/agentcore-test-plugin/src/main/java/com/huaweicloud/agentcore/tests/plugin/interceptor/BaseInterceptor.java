package com.huaweicloud.agentcore.tests.plugin.interceptor;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;
import com.huaweicloud.sermant.core.service.ServiceManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.DynamicConfigService;

public class BaseInterceptor extends AbstractInterceptor {
    DynamicConfigService dynamicConfigService = ServiceManager.getService(DynamicConfigService.class);
    @Override
    public ExecuteContext before(ExecuteContext context) throws Exception {
        return null;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) throws Exception {
        return null;
    }
}
