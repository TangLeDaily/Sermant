package com.huaweicloud.agentcore.tests.plugin.declarer;

import com.huaweicloud.agentcore.tests.plugin.interceptor.AddConfigListenerInterceptor;
import com.huaweicloud.agentcore.tests.plugin.interceptor.AddGroupConfigListenerInterceptor;
import com.huaweicloud.agentcore.tests.plugin.interceptor.GetConfigInterceptor;
import com.huaweicloud.agentcore.tests.plugin.interceptor.PublishConfigInterceptor;
import com.huaweicloud.agentcore.tests.plugin.interceptor.RemoveConfigInterceptor;
import com.huaweicloud.agentcore.tests.plugin.interceptor.RemoveConfigListenerInterceptor;
import com.huaweicloud.agentcore.tests.plugin.interceptor.RemoveGroupConfigListenerInterceptor;
import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

public class TestDynamicConfigDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.nameEquals("com.huaweicloud.agentcore.tests.application.DynamicConfigTest");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[] {
                InterceptDeclarer.build(MethodMatcher.nameEquals("publishConfig"), new PublishConfigInterceptor()),
                InterceptDeclarer.build(MethodMatcher.nameEquals("getConfig"), new GetConfigInterceptor()),
                InterceptDeclarer.build(MethodMatcher.nameEquals("removeConfig"), new RemoveConfigInterceptor()),
                InterceptDeclarer.build(MethodMatcher.nameEquals("addConfigListener"), new AddConfigListenerInterceptor()),
                InterceptDeclarer.build(MethodMatcher.nameEquals("removeConfigListener"), new RemoveConfigListenerInterceptor()),
                InterceptDeclarer.build(MethodMatcher.nameEquals("addGroupConfigListener"), new AddGroupConfigListenerInterceptor()),
                InterceptDeclarer.build(MethodMatcher.nameEquals("removeGroupConfigListener"),
                        new RemoveGroupConfigListenerInterceptor())};
    }
}
