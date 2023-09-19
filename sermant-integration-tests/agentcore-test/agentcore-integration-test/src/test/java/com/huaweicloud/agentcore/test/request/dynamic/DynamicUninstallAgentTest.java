package com.huaweicloud.agentcore.test.request.dynamic;

import com.huaweicloud.agentcore.test.request.utils.RequestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.IOException;

@EnabledIfSystemProperty(named = "sermant.agentcore.test.type", matches = "UNINSTALL_AGENT")
public class DynamicUninstallAgentTest {
    @Test
    public void testInstallPlugin() throws IOException {
        RequestUtils.testRequest("http://127.0.0.1:8915/testUninstallAgent");
    }
}
