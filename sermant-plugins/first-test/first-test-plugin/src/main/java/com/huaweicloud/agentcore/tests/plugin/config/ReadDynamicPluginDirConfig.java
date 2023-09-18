package com.huaweicloud.agentcore.tests.plugin.config;

import com.huaweicloud.sermant.core.config.common.ConfigFieldKey;
import com.huaweicloud.sermant.core.config.common.ConfigTypeKey;
import com.huaweicloud.sermant.core.plugin.config.PluginConfig;
import com.huaweicloud.sermant.core.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigTypeKey("dynamic.first.config")
public class ReadDynamicPluginDirConfig implements PluginConfig {

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