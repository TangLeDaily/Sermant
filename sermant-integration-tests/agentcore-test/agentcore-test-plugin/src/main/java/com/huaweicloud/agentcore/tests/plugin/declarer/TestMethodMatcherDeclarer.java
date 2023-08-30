package com.huaweicloud.agentcore.tests.plugin.declarer;

import com.huaweicloud.agentcore.tests.plugin.interceptor.SetEnhanceFlagInterceptor;
import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

/**
 * 测试方法匹配
 *
 * @author luanwenfei
 * @since 2022-10-24
 */
public class TestMethodMatcherDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.nameEquals("com.huaweicloud.agentcore.tests.application.MethodMatchersTest");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[] {
            InterceptDeclarer.build(MethodMatcher.isConstructor(), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.isStaticMethod(), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.nameEquals("exactNameMethod"), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.nameEquals("exactNameMethod"), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.namePrefixedWith("prefix"), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.nameInfixedWith("Infix"), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.nameSuffixedWith("Suffix"), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.resultTypeEquals(boolean.class), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.paramCountEquals(3), new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(MethodMatcher.paramTypesEqual(boolean.class, boolean.class),
                new SetEnhanceFlagInterceptor()),
            InterceptDeclarer.build(
                MethodMatcher.isAnnotatedWith("com.huaweicloud.agentcore.tests.common.TestAnnotationA"),
                new SetEnhanceFlagInterceptor()),
            InterceptDeclarer
                .build(
                    MethodMatcher.isAnnotatedWith("com.huaweicloud.agentcore.tests.common.TestAnnotationA",
                        "com.huaweicloud.agentcore.tests.common.TestAnnotationB"),
                    new SetEnhanceFlagInterceptor())};
    }
}
