package com.huaweicloud.agentcore.tests.plugin.declarer;

import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.Interceptor;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

/**
 * 测试系统类的增强能力
 *
 * @author luanwenfei
 * @since 2022-11-19
 */
public class TestBootstrapDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.nameEquals("java.lang.Thread");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[] {
            InterceptDeclarer.build(MethodMatcher.nameEquals("getAllStackTraces"), new Interceptor() {
                @Override
                public ExecuteContext before(ExecuteContext context) throws Exception {
                    System.out.println("getAllStackTraces");
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
            }), // 测试静态方法
            InterceptDeclarer.build(MethodMatcher.isConstructor().and(MethodMatcher.paramCountEquals(0)),
                new Interceptor() {
                    @Override
                    public ExecuteContext before(ExecuteContext context) throws Exception {
                        System.out.println("Constructor");
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
                }), // 测试无参构造函数
            InterceptDeclarer.build(MethodMatcher.nameEquals("setName"), new Interceptor() {
                @Override
                public ExecuteContext before(ExecuteContext context) throws Exception {
                    context.getArguments()[0] = "ABC";
                    System.out.println("setName");
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
                InterceptDeclarer.build(MethodMatcher.nameEquals("activeCount"), new Interceptor() {
                    @Override
                    public ExecuteContext before(ExecuteContext context) throws Exception {
                        System.out.println("activeCount");
                        return context.skip(100);
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

        };
    }
}
