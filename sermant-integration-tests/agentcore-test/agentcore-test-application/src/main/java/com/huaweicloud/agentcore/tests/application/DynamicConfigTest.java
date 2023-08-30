package com.huaweicloud.agentcore.tests.application;

public class DynamicConfigTest {
    public void testPublishAndRemove(){
        publishConfig(false, "testKey", "testGroup", "testContent");
        getConfig(false,"testKey", "testGroup", "testContent");
        removeConfig(false, "testKey", "testGroup");
        getConfig(false,"testKey", "testGroup", "");
    }

    public void testAddConfigListener(){

        addConfigListener(false, "testKey", "testGroup");
        publishConfig(false, "testKey", "testGroup", "testContent");
    }
    public void publishConfig(boolean enhanceFlag, String key, String group, String content){
        if (enhanceFlag){

        }
    }

    public void getConfig(boolean enhanceFlag, String key, String group, String predictContent){
        if (enhanceFlag){

        }
    }
    public void removeConfig(boolean enhanceFlag, String key, String group){
        if (enhanceFlag){

        }
    }

    public void addConfigListener(boolean enhanceFlag, String key, String group){
        if (enhanceFlag){

        }
    }

    public void removeConfigListener(boolean enhanceFlag, String key, String group){
        if (enhanceFlag){

        }
    }

    public void addGroupConfigListener(boolean enhanceFlag, String group){
        if (enhanceFlag){

        }
    }

    public void removeGroupConfigListener(boolean enhanceFlag, String group){
        if (enhanceFlag){

        }
    }

    public void subscribe(boolean enhanceFlag, String serviceName, String pluginName, String key){
        if (enhanceFlag){

        }
    }

    public void unSubscribe(boolean enhanceFlag, String serviceName, String pluginName, String key){
        if (enhanceFlag){

        }
    }
}
