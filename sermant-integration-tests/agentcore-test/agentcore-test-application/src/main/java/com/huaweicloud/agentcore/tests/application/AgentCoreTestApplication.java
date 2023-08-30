package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.results.TestCase;
import com.huaweicloud.agentcore.tests.results.TestResults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 用于对agentcore进行集成测试的宿主应用
 *
 * @author luanwenfei
 * @since 2022-10-12
 */
public class AgentCoreTestApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentCoreTestApplication.class);

    public static void main(String[] args) throws IllegalAccessException {
        // 测试类匹配
        ClassMatchersTest classMatchersTest = new ClassMatchersTest();
        classMatchersTest.testClassMatchers();

        // 测试方法匹配
        MethodMatchersTest methodMatchersTest = new MethodMatchersTest(false);
        methodMatchersTest.testMethodMatchers();

        // 测试增强能力
        EnhancementTest enhancementTest = new EnhancementTest();
        enhancementTest.testEnhancement();

        printResult();
    }

    public static void printResult() throws IllegalAccessException {
        for(Field field : TestResults.class.getFields()){
            TestCase testCase = (TestCase) field.get(null);
            assert testCase.isResult() : testCase.getDescription();
            System.out.println(testCase);
        }
    }
}
