package com.huaweicloud.agentcore.test.request.dynamic;

import com.huaweicloud.agentcore.test.request.utils.RequestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.IOException;

@EnabledIfSystemProperty(named = "agentcore.test.type", matches = "INSTALL_PLUGIN")
public class DynamicInstallPluginTest {
    @Test
    public void testInstallPlugin() throws IOException {
        RequestUtils.testRequest("http://127.0.0.1:8915/testInstallPlugin");
    }
}
