package com.huaweicloud.agentcore.tests.plugin.interceptor;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigListener;

/**
 * 执行配置发布
 *
 * @author tangle
 * @since 2023-8-30
 */
public class SubscribeInterceptor extends BaseInterceptor {
    @Override
    public ExecuteContext before(ExecuteContext context) {
        String key = (String) context.getArguments()[1];
        String group = (String) context.getArguments()[2];

        context.getArguments()[0] = dynamicConfigService.doAddConfigListener(key, group, new DynamicConfigListener() {
            final String listenerGroup = (String) context.getArguments()[3];

            @Override
            public void process(DynamicConfigEvent event) {
                // 发布一个配置到nacos，异步获取监听结果
                dynamicConfigService.doPublishConfig(buildKey(event.getKey(),event.getGroup()), listenerGroup,
                        buildContent(event.getContent()));
            }

            public String buildKey(String orinKey, String orinGroup) {
                return orinKey + "-" + orinGroup;
            }

            public String buildContent(String orinContent) {
                return "Success listener the config modify: " + orinContent;
            }
        });
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
