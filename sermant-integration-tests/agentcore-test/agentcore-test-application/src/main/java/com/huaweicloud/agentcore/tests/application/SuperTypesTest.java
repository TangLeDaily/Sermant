package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.common.TestSuperTypeA;
import com.huaweicloud.agentcore.tests.common.TestSuperTypeB;
import com.huaweicloud.agentcore.tests.results.TestResults;

/**
 * 用于测试超类匹配模式
 *
 * @author luanwenfei
 * @since 2022-10-13
 */
public class SuperTypesTest implements TestSuperTypeA, TestSuperTypeB {
    public static void staticFunction(boolean enhanceFlag) {
        if (enhanceFlag){
            TestResults.MATCHER_CLASS_BY_SUPER_TYPES.setResult(true);
        }
    }
}
