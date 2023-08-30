package com.huaweicloud.agentcore.tests.plugin.declarer;

import com.huaweicloud.agentcore.tests.plugin.interceptor.SetEnhanceFlagInterceptor;
import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.Interceptor;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

import java.util.logging.Logger;

/**
 * 测试超类匹配类
 *
 * @author luanwenfei
 * @since 2022-10-13
 */
public class TestSuperTypesDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.isExtendedFrom("com.huaweicloud.agentcore.tests.common.TestSuperTypeA","com.huaweicloud.agentcore.tests.common.TestSuperTypeB");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[] {
                InterceptDeclarer.build(MethodMatcher.isStaticMethod(), new SetEnhanceFlagInterceptor())};
    }
}
