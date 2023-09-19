package com.huaweicloud.agentcore.test.application.tests.dynamic;

import com.huaweicloud.agentcore.test.application.results.DynamicResults;

public class DynamicTest {
    /**
     * 用于测试插件反射修改的回执结果：监听成功
     */
    private static boolean ServiceCloseSuccess;

    public static void setServiceCloseSuccess(boolean flag) {
        ServiceCloseSuccess = flag;
    }

    public void testInstallPlugin() {
        String[] result = repeatEnhance(false, false).split("/");
        if (result.length == 2 && result[0].equals("true") && result[1].equals("true")) {
            DynamicResults.DYNAMIC_INSTALL_REPEAT_ENHANCE.setResult(true);
        }
    }

    public void testUninstallPlugin() {
        String[] result = repeatEnhance(false, false).split("/");
        if (result.length == 2 && result[0].equals("false")) {
            DynamicResults.DYNAMIC_UNINSTALL_INTERCEPTOR_FAIL.setResult(true);
        }
        if (result.length == 2 && result[1].equals("true")) {
            DynamicResults.DYNAMIC_UNINSTALL_REPEAT_ENHANCE.setResult(true);
        }
    }

    public void testUninstallAgent() {
        String[] result = repeatEnhance(false, false).split("/");
        if (result.length == 2 && result[0].equals("false") && result[1].equals("false")) {
            DynamicResults.DYNAMIC_UNINSTALL_AGENT_INTERCEPTOR_FAIL.setResult(true);
        }
    }

    public void testReInstallAgent() {
        String[] result = repeatEnhance(false, false).split("/");
        if (result.length == 2 && result[0].equals("true") && result[1].equals("true")) {
            DynamicResults.DYNAMIC_REINSTALL_AGENT_INTERCEPTOR_SUCCESS.setResult(true);
        }
    }

//    public void testPluginServiceClose(){
//        DynamicResults.DYNAMIC_UNINSTALL_SERVICE_CLOSE.setResult(ServiceCloseSuccess);
//    }

    private String repeatEnhance(boolean firstEnhanceFlag, boolean secondEnhanceFlag) {
        return firstEnhanceFlag + "/" + secondEnhanceFlag;
    }
}
