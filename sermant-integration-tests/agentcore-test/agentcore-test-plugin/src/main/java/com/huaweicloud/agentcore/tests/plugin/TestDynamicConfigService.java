package com.huaweicloud.agentcore.tests.plugin;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.plugin.service.PluginService;
import com.huaweicloud.sermant.core.service.ServiceManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.DynamicConfigService;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigListener;

import java.util.logging.Logger;

/**
 * 用于测试动态配置
 *
 * @author luanwenfei
 * @since 2022-10-13
 */
public class TestDynamicConfigService implements PluginService {
    @Override
    public void start() {
        Logger logger = LoggerFactory.getLogger();
        DynamicConfigService service = ServiceManager.getService(DynamicConfigService.class);
        logger.severe(service.getConfig("test", "sermant"));
        service.addConfigListener("test", "sermant", new DynamicConfigListener() {
            @Override
            public void process(DynamicConfigEvent event) {
                logger.severe(event.toString());
            }
        });
    }
}
