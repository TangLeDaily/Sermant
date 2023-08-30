package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.common.TestAnnotationA;
import com.huaweicloud.agentcore.tests.common.TestAnnotationB;
import com.huaweicloud.agentcore.tests.results.TestResults;

/**
 * 测试方法匹配相关功能
 *
 * @author luanwenfei
 * @since 2022-10-22
 */
public class MethodMatchersTest {
    public void testMethodMatchers() {
        MethodMatchersTest.staticMethod(false);
        exactNameMethod(false);
        prefixNameMethod(false);
        nameInfixMethod(false);
        methodNameSuffix(false);
        returnType(false);
        argumentsCount(false, 1, "A");
        argumentsType(false, false);
        byAnnotation(false);
        byAnnotations(false);
    }

    public MethodMatchersTest(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_CLASS_BY_CLASS_NAME_EXACTLY.setResult(true);
            TestResults.MATCHER_CONSTRUCTOR.setResult(true);
        }
    }

    public static void staticMethod(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_STATIC_METHODS.setResult(true);
        }
    }

    private void exactNameMethod(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_METHOD_NAME_EXACTLY.setResult(true);
        }
    }

    private void prefixNameMethod(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_METHOD_NAME_PREFIX.setResult(true);
        }
    }

    private void nameInfixMethod(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_METHOD_NAME_INFIX.setResult(true);
        }
    }

    private void methodNameSuffix(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_METHOD_NAME_SUFFIX.setResult(true);
        }
    }

    private boolean returnType(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_RETURN_TYPE.setResult(true);
        }
        return false;
    }

    private void argumentsCount(boolean enhanceFlag, int argA, String argB) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_ARGUMENTS_COUNT.setResult(true);
        }
    }

    private void argumentsType(boolean enhanceFlag, boolean arg) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_ARGUMENTS_TYPE.setResult(true);
        }
    }

    @TestAnnotationA
    private void byAnnotation(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_ANNOTATION.setResult(true);
        }
    }

    @TestAnnotationA
    @TestAnnotationB
    private void byAnnotations(boolean enhanceFlag) {
        if (enhanceFlag) {
            TestResults.MATCHER_METHOD_BY_ANNOTATIONS.setResult(true);
        }
    }
}
