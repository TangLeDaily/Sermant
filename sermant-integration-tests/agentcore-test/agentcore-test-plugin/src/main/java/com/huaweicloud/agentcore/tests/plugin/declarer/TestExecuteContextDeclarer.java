package com.huaweicloud.agentcore.tests.plugin.declarer;

import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.Interceptor;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

/**
 * 测试ExecuteContext增强能力
 *
 * @author luanwenfei
 * @since 2022-10-13
 */
public class TestExecuteContextDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.nameEquals("com.huaweicloud.agentcore.tests.application.EnhancementTest");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[]{
                InterceptDeclarer.build(MethodMatcher.nameEquals("testSkipFunction"), new Interceptor() {
                    @Override
                    public ExecuteContext before(ExecuteContext context) throws Exception {
                        context.skip(true);
                        return context;
                    }

                    @Override
                    public ExecuteContext after(ExecuteContext context) throws Exception {
                        return context;
                    }

                    @Override
                    public ExecuteContext onThrow(ExecuteContext context) throws Exception {
                        return context;
                    }
                }),
                InterceptDeclarer.build(MethodMatcher.nameEquals("testSetFiledFunction"), new Interceptor() {
                    @Override
                    public ExecuteContext before(ExecuteContext context) throws Exception {
                        context.setStaticFieldValue("staticField","staticFieldSetBySermant");
                        context.setMemberFieldValue("memberField","memberFieldSetBySermant");
                        return context;
                    }

                    @Override
                    public ExecuteContext after(ExecuteContext context) throws Exception {
                        return context;
                    }

                    @Override
                    public ExecuteContext onThrow(ExecuteContext context) throws Exception {
                        return context;
                    }
                }),
                InterceptDeclarer.build(MethodMatcher.nameEquals("testSetArguments"), new Interceptor() {
                    @Override
                    public ExecuteContext before(ExecuteContext context) throws Exception {
                        context.getArguments()[0] = "argSetBySermant";
                        return null;
                    }

                    @Override
                    public ExecuteContext after(ExecuteContext context) throws Exception {
                        return null;
                    }

                    @Override
                    public ExecuteContext onThrow(ExecuteContext context) throws Exception {
                        return null;
                    }
                })
        };
    }
}
