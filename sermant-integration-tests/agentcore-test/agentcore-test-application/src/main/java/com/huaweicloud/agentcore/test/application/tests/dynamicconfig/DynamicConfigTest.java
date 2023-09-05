/*
 * Copyright (C) 2023-2023 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaweicloud.agentcore.test.application.tests.dynamicconfig;

import com.huaweicloud.agentcore.test.application.results.DynamicConfigResults;

/**
 * 测试动态配置核心功能
 *
 * @author tangle
 * @since 2023-09-08
 */
public class DynamicConfigTest {
    /**
     * 测试所用的配置名、配置组、配置内容等
     */
    private static final String TEST_KEY_1 = "testKey1";

    private static final String TEST_KEY_2 = "testKey2";

    private static final String TEST_GROUP = "testGroup";

    private static final String TEST_CONTENT = "testContent";

    private static final String TEST_MODIFY_CONTENT = "testModifyContent";

    private static final long SLEEP_TIME_MILLIS = 1000L;

    /**
     * 用于测试插件反射修改的回执结果：监听成功
     */
    private static boolean listenerSuccess;

    public static void setListenerSuccess(boolean flag) {
        listenerSuccess = flag;
    }

    /**
     * 测试动态配置功能
     *
     * @throws InterruptedException
     */
    public void testDynamicConfig() throws InterruptedException {
        testPublishAndRemoveConfig();
        testAddConfigListener();
        testRemoveConfigListener();
        testAddGroupConfigListener();
        testRemoveGroupConfigListener();
    }

    /**
     * 测试发布和移除配置
     *
     * @throws InterruptedException
     */
    public void testPublishAndRemoveConfig() throws InterruptedException {
        boolean result = true;

        // 发布配置
        result &= publishConfig(false, TEST_KEY_1, TEST_GROUP, TEST_CONTENT);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 获取配置
        result &= getConfig(false, TEST_KEY_1, TEST_GROUP, TEST_CONTENT);
        DynamicConfigResults.DYNAMIC_PUBLISH_CONFIG.setResult(result);

        // 删除配置
        result &= removeConfig(false, TEST_KEY_1, TEST_GROUP);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 再次获取配置，预计获取为空
        result &= getConfig(false, TEST_KEY_1, TEST_GROUP, "");
        DynamicConfigResults.DYNAMIC_REMOVE_CONFIG.setResult(result);
    }

    /**
     * 测试添加单一监听
     *
     * @throws InterruptedException
     */
    public void testAddConfigListener() throws InterruptedException {
        boolean result = true;

        // 添加单一配置监听
        listenerSuccess = false;
        result &= addConfigListener(false, TEST_KEY_1, TEST_GROUP);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 发布配置
        result &= publishConfig(false, TEST_KEY_1, TEST_GROUP, TEST_CONTENT);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 查看监听结果，预计为true
        DynamicConfigResults.DYNAMIC_ADD_CONFIG_LISTENER.setResult(result & listenerSuccess);
    }

    /**
     * 测试移除单一监听
     *
     * @throws InterruptedException
     */
    public void testRemoveConfigListener() throws InterruptedException {
        boolean result = true;

        // 删除单一配置监听
        result &= removeConfigListener(false, TEST_KEY_1, TEST_GROUP);
        listenerSuccess = false;
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 修改配置
        result &= publishConfig(false, TEST_KEY_1, TEST_GROUP, TEST_MODIFY_CONTENT);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 查看监听结果，预计为false
        DynamicConfigResults.DYNAMIC_REMOVE_CONFIG_LISTENER.setResult(result & (!listenerSuccess));
    }

    /**
     * 测试添加组监听
     *
     * @throws InterruptedException
     */
    public void testAddGroupConfigListener() throws InterruptedException {
        boolean result = true;

        // 发布配置1和2
        result &= publishConfig(false, TEST_KEY_1, TEST_GROUP, TEST_CONTENT);
        result &= publishConfig(false, TEST_KEY_2, TEST_GROUP, TEST_CONTENT);
        Thread.sleep(SLEEP_TIME_MILLIS);
        listenerSuccess = false;

        // 添加组配置监听
        result &= addGroupConfigListener(false, TEST_GROUP);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 修改配置1
        result &= publishConfig(false, TEST_KEY_1, TEST_GROUP, TEST_MODIFY_CONTENT);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 查看监听结果，预计为true
        DynamicConfigResults.DYNAMIC_ADD_GROUP_CONFIG_LISTENER.setResult(result & listenerSuccess);
    }

    /**
     * 测试移除组监听
     *
     * @throws InterruptedException
     */
    public void testRemoveGroupConfigListener() throws InterruptedException {
        boolean result = true;

        // 删除单一配置监听
        result &= removeGroupConfigListener(false, TEST_GROUP);
        Thread.sleep(SLEEP_TIME_MILLIS);
        listenerSuccess = false;

        // 删除配置1和2
        result &= removeConfig(false, TEST_KEY_1, TEST_GROUP);
        result &= removeConfig(false, TEST_KEY_2, TEST_GROUP);
        Thread.sleep(SLEEP_TIME_MILLIS);

        // 查看监听结果，预计为false
        DynamicConfigResults.DYNAMIC_REMOVE_GROUP_CONFIG_LISTENER.setResult(result & (!listenerSuccess));
    }

    /**
     * 测试插件拦截方法：发布配置
     *
     * @param enhanceFlag 回执标识
     * @param key 配置key
     * @param group 配置组
     * @param content 配置内容
     * @return 回执标识
     */
    public boolean publishConfig(boolean enhanceFlag, String key, String group, String content) {
        return enhanceFlag;
    }

    /**
     * 测试插件拦截方法：获取配置
     *
     * @param enhanceFlag 回执标识
     * @param key 配置key
     * @param group 配置组
     * @param predictContent 预测获取到的配置内容
     * @return 回执标识
     */
    public boolean getConfig(boolean enhanceFlag, String key, String group, String predictContent) {
        return enhanceFlag;
    }

    /**
     * 测试插件拦截方法：移除配置
     *
     * @param enhanceFlag 回执标识
     * @param key 配置key
     * @param group 配置组
     * @return 回执标识
     */
    public boolean removeConfig(boolean enhanceFlag, String key, String group) {
        return enhanceFlag;
    }

    /**
     * 测试插件拦截方法：添加单一监听
     *
     * @param enhanceFlag 回执标识
     * @param key 配置key
     * @param group 配置组
     * @return 回执标识
     */
    public boolean addConfigListener(boolean enhanceFlag, String key, String group) {
        return enhanceFlag;
    }

    /**
     * 测试插件拦截方法：移除单一监听
     *
     * @param enhanceFlag 回执标识
     * @param key 配置key
     * @param group 配置组
     * @return 回执标识
     */
    public boolean removeConfigListener(boolean enhanceFlag, String key, String group) {
        return enhanceFlag;
    }

    /**
     * 测试插件拦截方法：添加组监听
     *
     * @param enhanceFlag 回执标识
     * @param group 配置组
     * @return 回执标识
     */
    public boolean addGroupConfigListener(boolean enhanceFlag, String group) {
        return enhanceFlag;
    }

    /**
     * 测试插件拦截方法：移除组监听
     *
     * @param enhanceFlag 回执标识
     * @param group 配置组
     * @return 回执标识
     */
    public boolean removeGroupConfigListener(boolean enhanceFlag, String group) {
        return enhanceFlag;
    }
}
