package com.huaweicloud.sermant.implement.service.dynamicconfig.nacos;

import com.huaweicloud.sermant.core.config.ConfigManager;
import com.huaweicloud.sermant.core.plugin.config.ServiceMeta;
import com.huaweicloud.sermant.core.service.ServiceManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigListener;
import com.huaweicloud.sermant.core.service.dynamicconfig.config.DynamicConfig;
import com.huaweicloud.sermant.core.utils.AesUtil;

import org.junit.After;
import org.junit.Before;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Optional;

public class NacosBaseTest {
    public NacosDynamicConfigService nacosDynamicConfigService;

    public final DynamicConfig dynamicConfig = new DynamicConfig();

    public final ServiceMeta serviceMeta = new ServiceMeta();

    public MockedStatic<ConfigManager> dynamicConfigMockedStatic;

    public MockedStatic<ServiceManager> serviceManagerMockedStatic;

    public class TestListener implements DynamicConfigListener {
        private boolean isChange = false;

        @Override
        public void process(DynamicConfigEvent event) {
            setChange(true);
            System.out.println(
                    "test Listener process:" + event.getKey() + " / " + event.getGroup() + " / " + event.getContent());
        }

        public boolean isChange() {
            return isChange;
        }

        public void setChange(boolean change) {
            isChange = change;
        }
    }

    @Before
    public void initConfig() {
        dynamicConfig.setEnableAuth(true);
        dynamicConfig.setServerAddress("127.0.0.1:8848");
        dynamicConfig.setTimeoutValue(30000);
        Optional<String> optional = AesUtil.generateKey();
        dynamicConfig.setPrivateKey(optional.orElse(""));
        dynamicConfig.setUserName(AesUtil.encrypt(optional.get(), "nacos").orElse(""));
        dynamicConfig.setPassword(AesUtil.encrypt(optional.get(), "nacos").orElse(""));

        serviceMeta.setProject("testProject2");
        serviceMeta.setApplication("testApplication");
        serviceMeta.setEnvironment("testEnvironment");
        serviceMeta.setCustomLabel("testCustomLabel");
        serviceMeta.setCustomLabelValue("testCustomLabelValue");
        dynamicConfigMockedStatic = Mockito.mockStatic(ConfigManager.class);
        dynamicConfigMockedStatic.when(() -> ConfigManager.getConfig(DynamicConfig.class))
                .thenReturn(dynamicConfig);
        dynamicConfigMockedStatic.when(() -> ConfigManager.getConfig(ServiceMeta.class))
                .thenReturn(serviceMeta);

        nacosDynamicConfigService = new NacosDynamicConfigService();
        nacosDynamicConfigService.start();

        serviceManagerMockedStatic = Mockito.mockStatic(ServiceManager.class);
        serviceManagerMockedStatic.when(() -> ServiceManager.getService(NacosDynamicConfigService.class))
                .thenReturn(nacosDynamicConfigService);
    }

    @After
    public void closeMock() {
        dynamicConfigMockedStatic.close();
        serviceManagerMockedStatic.close();
    }
}
