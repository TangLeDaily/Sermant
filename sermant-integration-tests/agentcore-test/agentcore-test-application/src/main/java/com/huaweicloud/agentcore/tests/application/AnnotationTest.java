package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.common.TestAnnotationA;
import com.huaweicloud.agentcore.tests.results.TestResults;

/**
 * 测试通过单个注解匹配类
 *
 * @author luanwenfei
 * @since 2022-10-24
 */
@TestAnnotationA
public class AnnotationTest {
    public static void staticFunction(boolean enhanceFlag) {
        if (enhanceFlag){
            TestResults.MATCHER_CLASS_BY_ANNOTATION.setResult(true);
        }
    }
}
