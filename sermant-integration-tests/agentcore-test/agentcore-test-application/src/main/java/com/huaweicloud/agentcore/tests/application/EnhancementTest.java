package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.results.TestResults;

/**
 * 测试增强能力
 *
 * @author luanwenfei
 * @since 2022-10-24
 */
public class EnhancementTest {
    private static String staticField = "staticField";

    private String memberField = "memberField";

    public void testEnhancement() {
        TestResults.SKIP_METHOD.setResult(true);
        if (testSkipFunction()) {
            TestResults.MODIFY_RESULT.setResult(true);
        }
        testSetFiledFunction();
        testSetArguments("arg");
    }

    private boolean testSkipFunction() {
        TestResults.SKIP_METHOD.setResult(false);
        return false;
    }

    private void testSetFiledFunction() {
        if ("staticFieldSetBySermant".equals(staticField)) {
            TestResults.MODIFY_STATIC_FIELDS.setResult(true);
        }
        if ("memberFieldSetBySermant".equals(memberField)) {
            TestResults.MODIFY_MEMBER_FIELDS.setResult(true);
        }
    }

    private void testSetArguments(String arg) {
        if ("argSetBySermant".equals(arg)) {
            TestResults.MODIFY_ARGUMENTS.setResult(true);
        }
    }
}
