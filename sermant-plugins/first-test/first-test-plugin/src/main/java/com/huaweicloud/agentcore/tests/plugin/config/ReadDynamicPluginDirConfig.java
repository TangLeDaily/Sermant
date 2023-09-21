package com.huaweicloud.agentcore.tests.plugin.config;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.config.common.ConfigFieldKey;
import com.huaweicloud.sermant.core.config.common.ConfigTypeKey;
import com.huaweicloud.sermant.core.plugin.config.PluginConfig;
import com.huaweicloud.sermant.core.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ConfigTypeKey("dynamic.first.config")
public class ReadDynamicPluginDirConfig implements PluginConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    @ConfigFieldKey("configSuccess")
    private boolean configSuccess;

    @ConfigFieldKey("configLabel")
    private String configLabel;

    @Override
    public String toString() {
        return "ReadDynamicPluginDirConfig{"
                + "configSuccess=" + configSuccess
                + ", configLabel=" + configLabel
                + '}';
    }

    public ReadDynamicPluginDirConfig(){
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(this::printConfig, 3000L,
                3000L,
                TimeUnit.MILLISECONDS);
    }

    public void printConfig(){
        LOGGER.log(Level.INFO, toString());
    }

    public boolean isConfigSuccess() {
        return configSuccess;
    }

    public String getConfigLabel() {
        return configLabel;
    }

    public void setConfigSuccess(boolean configSuccess) {
        this.configSuccess = configSuccess;
    }

    public void setConfigLabel(String configLabel) {
        this.configLabel = configLabel;
    }
}