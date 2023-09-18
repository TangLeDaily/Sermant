package com.huaweicloud.agentcore.tests.plugin.service;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.plugin.service.PluginService;
import com.huaweicloud.sermant.core.service.ServiceManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.DynamicConfigService;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirstTestService implements PluginService {
    static final Logger LOGGER = LoggerFactory.getLogger();
    @Override
    public void start() {
        LOGGER.log(Level.INFO, "FirstTestService load success.");
    }

    @Override
    public void stop() {
        LOGGER.log(Level.INFO, "FirstTestService stop success.");
    }
}
