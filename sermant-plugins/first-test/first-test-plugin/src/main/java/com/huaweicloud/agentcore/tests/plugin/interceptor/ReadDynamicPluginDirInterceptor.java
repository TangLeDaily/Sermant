package com.huaweicloud.agentcore.tests.plugin.interceptor;

import com.huaweicloud.agentcore.tests.plugin.config.ReadDynamicPluginDirConfig;
import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;
import com.huaweicloud.sermant.core.plugin.config.PluginConfigManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadDynamicPluginDirInterceptor extends AbstractInterceptor {
    static final Logger LOGGER = LoggerFactory.getLogger();

    protected final ReadDynamicPluginDirConfig readDynamicPluginDirConfig = PluginConfigManager.getPluginConfig(ReadDynamicPluginDirConfig.class);
    @Override
    public ExecuteContext before(ExecuteContext context) {
        LOGGER.log(Level.INFO, "ReadDynamicPluginDirInterceptor entry success.");

        LOGGER.log(Level.INFO,
                "ReadDynamicPluginDirConfig result is: {0}", readDynamicPluginDirConfig.isConfigSuccess());
        LOGGER.log(Level.INFO,
                "ReadDynamicPluginDirConfig config label is: {0}", readDynamicPluginDirConfig.getConfigLabel());
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
