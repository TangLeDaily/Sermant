package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.results.TestResults;

/**
 * 测试通过前缀匹配类
 *
 * @author luanwenfei
 * @since 2022-10-24
 */
public class PrefixNameTest {
    public static void staticFunction(boolean enhanceFlag) {
        if (enhanceFlag){
            TestResults.MATCHER_CLASS_BY_CLASS_NAME_PREFIX.setResult(true);
        }
    }
}
