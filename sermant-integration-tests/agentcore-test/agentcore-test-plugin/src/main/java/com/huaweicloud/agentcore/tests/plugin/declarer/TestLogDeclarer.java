package com.huaweicloud.agentcore.tests.plugin.declarer;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.Interceptor;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

import java.util.logging.Logger;

/**
 * 用于测试日志模块的插件定义
 *
 * @author luanwenfei
 * @since 2022-10-12
 */
public class TestLogDeclarer extends AbstractPluginDeclarer {
    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.nameEquals("com.huaweicloud.agentcore.tests.application.AgentCoreTestApplication");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[] {
            InterceptDeclarer.build(MethodMatcher.nameEquals("memberFunction"), new Interceptor() {
                Logger logger = LoggerFactory.getLogger();
                @Override
                public ExecuteContext before(ExecuteContext context) throws Exception {
                    logger.fine("fine");
                    logger.info("info");
                    logger.warning("warning");
                    logger.severe("server");
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
            })};
    }
}
