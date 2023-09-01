package com.huaweicloud.agentcore.tests.application;

import com.huaweicloud.agentcore.tests.results.TestResults;

public class DynamicConfigTest {

    public void testDynamicConfig(){
        try {
            testPublishAndRemoveConfig();
            testAddConfigListener();
            testRemoveConfigListener();
            testAddGroupConfigListener();
            testRemoveGroupConfigListener();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    public void testPublishAndRemoveConfig() throws InterruptedException {
        boolean result = true;
        // 发布配置
        result &= publishConfig(false, "testKey", "testGroup", "testContent");
        System.out.println("publish-publish:"+result);
        Thread.sleep(1000);
        // 获取配置
        result &= getConfig(false, "testKey", "testGroup", "testContent");
        TestResults.DYNAMIC_PUBLISH_CONFIG.setResult(result);
        System.out.println("publish-getConfig:"+result);

        // 删除配置
        result &= removeConfig(false, "testKey", "testGroup");
        System.out.println("remove-removeConfig:"+result);
        Thread.sleep(1000);
        // 再次获取配置，预计获取为空
        result &= getConfig(false, "testKey", "testGroup", "");
        TestResults.DYNAMIC_REMOVE_CONFIG.setResult(result);
        System.out.println("remove-getConfig:"+result);
    }

    public void testAddConfigListener() throws InterruptedException {
        boolean result = true;
        // 添加单一配置监听
        result &= addConfigListener(false, "testKey", "testGroup", "testAsyncListenerGroup");
        System.out.println("remove-addConfigListener:"+result);
        Thread.sleep(1000);
        // 发布配置
        result &= publishConfig(false, "testKey", "testGroup", "testContent");
        System.out.println("remove-publishConfig:"+result);
        Thread.sleep(1000);
        // 获取单一配置监听的监听结果
        result &= getConfig(false, buildKey("testKey","testGroup"), "testAsyncListenerGroup", buildContent("testContent"));
        System.out.println("remove-getConfig:"+result);
        // 移除单一配置监听的监听结果
        result &= removeConfig(false, buildKey("testKey","testGroup"), "testAsyncListenerGroup");
        System.out.println("remove-removeConfig:"+result);
        Thread.sleep(1000);
        TestResults.DYNAMIC_ADD_CONFIG_LISTENER.setResult(result);
    }
    public void testRemoveConfigListener() throws InterruptedException {
        boolean result = true;
        // 删除单一配置监听
        result &= removeConfigListener(false, "testKey", "testGroup");
        Thread.sleep(1000);
        // 修改配置
        result &= publishConfig(false, "testKey", "testGroup", "testModifyContent");
        Thread.sleep(1000);
        // 获取单一配置监听的监听结果，预计为空
        result &= getConfig(false, buildKey("testKey","testGroup"), "testListenerGroup", "");
        // 移除配置
        result &= removeConfig(false, "testKey", "testGroup");
        Thread.sleep(1000);
        TestResults.DYNAMIC_REMOVE_CONFIG_LISTENER.setResult(result);
    }

    public void testAddGroupConfigListener() throws InterruptedException {
        boolean result = true;
        // 发布配置1和2
        result &= publishConfig(false, "testKey1", "testGroup", "testContent");
        result &= publishConfig(false, "testKey2", "testGroup", "testContent");
        System.out.println("testAddGroupConfigListener-publishConfig:"+result);
        Thread.sleep(1000);
        // 添加组配置监听
        result &= addGroupConfigListener(false, "testGroup", "testListenerGroup");
        System.out.println("testAddGroupConfigListener-addGroupConfigListener:"+result);
        Thread.sleep(1000);
        // 修改配置1
        result &= publishConfig(false, "testKey1", "testGroup", "testModifyContent");
        System.out.println("testAddGroupConfigListener-publishConfig:"+result);
        Thread.sleep(1000);
        // 获取配置1监听的监听结果
        result &= getConfig(false, buildKey("testKey1","testGroup"), "testListenerGroup", buildContent(
                "testModifyContent"));
        System.out.println("testAddGroupConfigListener-getConfig:"+result);
        // 获取配置1监听的监听结果
        result &= removeConfig(false, buildKey("testKey1","testGroup"), "testListenerGroup");
        System.out.println("testAddGroupConfigListener-removeConfig:"+result);
        Thread.sleep(1000);
        TestResults.DYNAMIC_ADD_GROUP_CONFIG_LISTENER.setResult(result);
    }

    public void testRemoveGroupConfigListener() throws InterruptedException {
        boolean result = true;
        // 删除单一配置监听
        result &= removeGroupConfigListener(false, "testGroup");
        System.out.println("testRemoveGroupConfigListener-removeGroupConfigListener:"+result);
        Thread.sleep(1000);
        // 删除配置1和2
        result &= removeConfig(false, "testKey1", "testGroup");
        result &= removeConfig(false, "testKey2", "testGroup");
        System.out.println("testRemoveGroupConfigListener-removeConfig:"+result);
        Thread.sleep(1000);
        // 获取配置监听的监听结果，预计为空
        result &= getConfig(false, buildKey("testKey1","testGroup"), "testListenerGroup", "");
        result &= getConfig(false, buildKey("testKey2","testGroup"), "testListenerGroup", "");
        System.out.println("testRemoveGroupConfigListener-getConfig:"+result);
        Thread.sleep(1000);
        TestResults.DYNAMIC_REMOVE_GROUP_CONFIG_LISTENER.setResult(result);
    }

    public boolean publishConfig(boolean enhanceFlag, String key, String group, String content) {
        return enhanceFlag;
    }

    public boolean getConfig(boolean enhanceFlag, String key, String group, String predictContent) {
        return enhanceFlag;
    }

    public boolean removeConfig(boolean enhanceFlag, String key, String group) {
        return enhanceFlag;
    }

    public boolean addConfigListener(boolean enhanceFlag, String key, String group, String listenerGroup) {
        return enhanceFlag;
    }

    public boolean removeConfigListener(boolean enhanceFlag, String key, String group) {
        return enhanceFlag;
    }

    public boolean addGroupConfigListener(boolean enhanceFlag, String group, String listenerGroup) {
        return enhanceFlag;
    }

    public boolean removeGroupConfigListener(boolean enhanceFlag, String group) {
        return enhanceFlag;
    }

    public void subscribe(boolean enhanceFlag, String serviceName, String pluginName, String key) {
        if (enhanceFlag) {

        }
    }

    public void unSubscribe(boolean enhanceFlag, String serviceName, String pluginName, String key) {
        if (enhanceFlag) {

        }
    }

    public String buildKey(String orinKey, String orinGroup) {
        return orinKey + "-" + orinGroup;
    }

    public String buildContent(String orinContent) {
        return "Success listener the config modify: " + orinContent;
    }
}
