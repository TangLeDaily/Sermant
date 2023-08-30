package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.common.TestAnnotationA;
import com.huaweicloud.agentcore.tests.common.TestAnnotationB;
import com.huaweicloud.agentcore.tests.results.TestResults;

/**
 * 用于测试注解匹配拦截
 *
 * @author luanwenfei
 * @since 2022-10-13
 */
@TestAnnotationA
@TestAnnotationB
public class AnnotationsTest {
    public static void staticFunction(boolean enhanceFlag) {
        if (enhanceFlag){
            TestResults.MATCHER_CLASS_BY_ANNOTATIONS.setResult(true);
        }
    }
}
