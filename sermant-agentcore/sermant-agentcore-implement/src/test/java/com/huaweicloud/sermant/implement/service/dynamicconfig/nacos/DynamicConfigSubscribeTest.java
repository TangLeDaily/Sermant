package com.huaweicloud.sermant.implement.service.dynamicconfig.nacos;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

public class DynamicConfigSubscribeTest extends NacosBaseTest{
    DynamicConfigSubscribe dynamicConfigSubscribe;
    public DynamicConfigSubscribeTest(){

    }
    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        try {
            // 测试订阅
            TestListener testListener = new TestListener();
            dynamicConfigSubscribe = new DynamicConfigSubscribe("testServiceName", testListener, "testPluginName",
                    "testSubscribeKey");
            Assert.assertTrue(dynamicConfigSubscribe.subscribe());
            Assert.assertTrue(
                    nacosDynamicConfigService.doPublishConfig("testSubscribeKey",
                            "app:testApplication_environment:testEnvironment",
                            "content:1"));
            Assert.assertTrue(
                    nacosDynamicConfigService.doPublishConfig("testSubscribeKey",
                            "app:testApplication_environment:testEnvironment_service:testServiceName",
                            "content:2"));
            Assert.assertTrue(
                    nacosDynamicConfigService.doPublishConfig("testSubscribeKey",
                            "testCustomLabel:testCustomLabelValue",
                            "content:3"));
            Thread.sleep(1000);
            Assert.assertTrue(testListener.isChange());
            Field fieldListener = nacosDynamicConfigService.getClass().getDeclaredField("listeners");
            fieldListener.setAccessible(true);
            Assert.assertEquals(3,
                    ((List<NacosListener>) fieldListener.get(nacosDynamicConfigService)).size());

            // 测试删除订阅
            Assert.assertTrue(dynamicConfigSubscribe.unsSubscribe());
            Assert.assertEquals(0,
                    ((List<NacosListener>) fieldListener.get(nacosDynamicConfigService)).size());
            testListener.setChange(false);
            Assert.assertTrue(
                    nacosDynamicConfigService.doPublishConfig("testSubscribeKey",
                            "app:testApplication_environment:testEnvironment",
                            "content:11"));
            Assert.assertTrue(
                    nacosDynamicConfigService.doPublishConfig("testSubscribeKey",
                            "app:testApplication_environment:testEnvironment_service:testServiceName",
                            "content:22"));
            Assert.assertTrue(
                    nacosDynamicConfigService.doPublishConfig("testSubscribeKey",
                            "testCustomLabel:testCustomLabelValue",
                            "content:33"));
            Thread.sleep(1000);
            Assert.assertFalse(testListener.isChange());
        } finally {
            nacosDynamicConfigService.doRemoveConfig("testSubscribeKey", "app:testApplication_environment:testEnvironment");
            nacosDynamicConfigService.doRemoveConfig("testSubscribeKey", "app:testApplication_environment:testEnvironment_service:testServiceName");
            nacosDynamicConfigService.doRemoveConfig("testSubscribeKey", "testCustomLabel:testCustomLabelValue");

        }
    }

}
