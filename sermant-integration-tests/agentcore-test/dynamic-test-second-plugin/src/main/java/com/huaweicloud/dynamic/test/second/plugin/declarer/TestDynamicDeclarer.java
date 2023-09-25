package com.huaweicloud.dynamic.test.second.plugin.declarer;

import com.huaweicloud.dynamic.test.second.plugin.interceptor.RepeatEnhanceInterceptor;
import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

public class TestDynamicDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.nameEquals(
                "com.huaweicloud.agentcore.test.application.tests.dynamic.DynamicTest");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[]{
                InterceptDeclarer.build(MethodMatcher.nameEquals("repeatEnhance"), new RepeatEnhanceInterceptor())
        };
    }
}
