package com.huaweicloud.agentcore.tests.plugin.declarer;

import com.huaweicloud.agentcore.tests.plugin.interceptor.SetEnhanceFlagInterceptor;
import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

/**
 * 测试注解匹配类
 *
 * @author luanwenfei
 * @since 2022-10-13
 */
public class TestAnnotationsDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.isAnnotatedWith("com.huaweicloud.agentcore.tests.common.TestAnnotationA","com.huaweicloud.agentcore.tests.common.TestAnnotationB");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[] {
                InterceptDeclarer.build(MethodMatcher.isStaticMethod(), new SetEnhanceFlagInterceptor())};
    }
}
