package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.common.TestSuperTypeA;
import com.huaweicloud.agentcore.tests.results.TestResults;

/**
 * 测试根据单个父类匹配
 *
 * @author luanwenfei
 * @since 2022-10-24
 */
public class SuperTypeTest implements TestSuperTypeA {
    public static void staticFunction(boolean enhanceFlag) {
        if (enhanceFlag){
            TestResults.MATCHER_CLASS_BY_SUPER_TYPE.setResult(true);
        }
    }
}
