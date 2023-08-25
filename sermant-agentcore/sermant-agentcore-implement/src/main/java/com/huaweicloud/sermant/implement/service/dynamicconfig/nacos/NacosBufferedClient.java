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

package com.huaweicloud.sermant.implement.service.dynamicconfig.nacos;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.config.ConfigManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.config.DynamicConfig;
import com.huaweicloud.sermant.core.utils.AesUtil;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.Closeable;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

/**
 * {@link ConfigService}的包装，封装nacos原生api，提供更易用的api
 *
 * @author tangle
 * @since 2023-08-17
 */
public class NacosBufferedClient implements Closeable {
    /**
     * configService连接状态type
     */
    public static final String KEY_CONNECTED = "UP";

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * 动态配置信息
     */
    private static final DynamicConfig CONFIG = ConfigManager.getConfig(DynamicConfig.class);

    private ConfigService configService;

    /**
     * 新建NacosBufferedClient，初始化nacos客户端
     *
     * @param connectString 连接字符串，必须形如：{@code host:port[(,host:port)...]}
     * @param sessionTimeout 会话超时时间
     * @param namespace 命名空间
     * @throws NacosInitException 依赖动态配置情况下，nacos初始化失败，需要中断Sermant
     */
    public NacosBufferedClient(String connectString, int sessionTimeout, String namespace) {
        Properties properties = createProperties(connectString, sessionTimeout, namespace);
        createConfigService(connectString, properties);
    }

    /**
     * 新建NacosBufferedClient，初始化nacos客户端
     *
     * @param connectString 连接字符串，必须形如：{@code host:port[(,host:port)...]}
     * @param sessionTimeout 会话超时时间
     * @param namespace 命名空间
     * @param userName 已加密的用户名
     * @param password 已加密的密码
     * @throws NacosInitException 依赖动态配置情况下，nacos初始化失败，需要中断Sermant
     */
    public NacosBufferedClient(String connectString, int sessionTimeout, String namespace, String userName,
            String password) {
        Properties properties = createProperties(connectString, sessionTimeout, namespace, userName, password);
        createConfigService(connectString, properties);
    }

    /**
     * 获取配置
     *
     * @param key 配置名称
     * @param group 配置所在组
     * @return 配置内容
     */
    public String getConfig(String key, String group) {
        final String data;
        try {
            data = getConfigService().getConfig(key, group, CONFIG.getRequestTimeout());
        } catch (NacosException | NacosInitException ignored) {
            return "";
        }
        return data == null ? "" : data;
    }

    /**
     * 发布配置
     *
     * @param key 配置名称
     * @param group 配置所在组
     * @param content 配置内容
     * @return 是否发布成功
     */
    public boolean publishConfig(String key, String group, String content) {
        boolean result;
        try {
            result = getConfigService().publishConfig(key, group, content);
        } catch (NacosException | NacosInitException ignored) {
            return false;
        }
        return result;
    }

    /**
     * 删除配置
     *
     * @param key 配置名称
     * @param group 配置所在组
     * @return 是否删除成功
     */
    public boolean removeConfig(String key, String group) {
        boolean result;
        try {
            result = getConfigService().removeConfig(key, group);
        } catch (NacosException | NacosInitException ignored) {
            return false;
        }
        return result;
    }

    /**
     * 添加监听
     *
     * @param key 配置名称
     * @param group 配置所在组
     * @param listener 监听器
     * @return 是否提那家成功
     */
    public boolean addListener(String key, String group, Listener listener) {
        try {
            // System.out.println(key+"/"+group+": "+configService.getConfig(key, group, 3000L));
            Listener listener1 = new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String s) {
                    System.out.println("true");
                }
            };
            Properties properties = createProperties("127.0.0.1:8848", 300000, "testProject2");
            ConfigService configService1 = NacosFactory.createConfigService(properties);
            configService1.addListener("key", "group", listener1);
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                System.out.println("eee");
            }
            System.out.println(configService1.getServerStatus());
            return true;
        } catch (NacosException | NacosInitException ignored) {
            return false;
        }
    }

    /**
     * 移除监听
     *
     * @param key 配置名称
     * @param group 配置所在组
     * @param listener 监听器
     * @return 是否移除成功
     */
    public boolean removeListener(String key, String group, Listener listener) {
        try {
            System.out.println(key+"/"+group+": "+"移除配置");
            getConfigService().removeListener(key, group, listener);
            return true;
        } catch (NacosInitException ignored) {
            return false;
        }
    }

    /**
     * 判断configService状态后获取configService
     *
     * @return configService
     * @throws NacosInitException nacos连接失败
     */
    private ConfigService getConfigService() {
        if (KEY_CONNECTED.equals(configService.getServerStatus())) {
            return configService;
        }
        throw new NacosInitException();
    }

    /**
     * 构建Properties
     *
     * @param connectString 连接字符串，必须形如：{@code host:port[(,host:port)...]}
     * @param sessionTimeout 会话超时时间
     * @param namespace 命名空间
     * @return Properties
     */
    private Properties createProperties(String connectString, int sessionTimeout, String namespace) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, connectString);
        properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        properties.setProperty(PropertyKeyConst.CONFIG_LONG_POLL_TIMEOUT, String.valueOf(sessionTimeout));
        return properties;
    }

    /**
     * 构建Properties
     *
     * @param connectString 连接字符串，必须形如：{@code host:port[(,host:port)...]}
     * @param sessionTimeout 会话超时时间
     * @param namespace 命名空间
     * @param userName 已加密的用户名
     * @param password 已加密的密码
     * @return Properties
     */
    private Properties createProperties(String connectString, int sessionTimeout, String namespace, String userName,
            String password) {
        Properties properties = this.createProperties(connectString, sessionTimeout, namespace);
        properties.setProperty(PropertyKeyConst.USERNAME,
                String.valueOf(AesUtil.decrypt(CONFIG.getPrivateKey(), userName)));
        properties.setProperty(PropertyKeyConst.PASSWORD,
                String.valueOf(AesUtil.decrypt(CONFIG.getPrivateKey(), password)));
        return properties;
    }

    /**
     * 构建configService
     *
     * @param connectString 连接字符串，必须形如：{@code host:port[(,host:port)...]}
     * @param properties 构建需要的配置
     * @throws NacosInitException nacos连接失败
     */
    private void createConfigService(String connectString, Properties properties) {
        try {
            if (!connectAndRetryConnect(properties)) {
                LOGGER.warning("Nacos connection reaches the maximum number of retries");
                throw new NacosInitException(connectString);
            }
        } catch (NacosException ignored) {
            throw new NacosInitException(connectString);
        }
    }

    /**
     * 连接nacos
     *
     * @param properties nacosClient连接配置信息
     * @return nacos连接状态
     * @throws NacosException nacos初始化异常
     */
    private boolean connectAndRetryConnect(Properties properties) throws NacosException {
        int tryNum = 0;
        while (tryNum++ <= CONFIG.getConnectRetryTimes()) {
            this.configService = NacosFactory.createConfigService(properties);
            if (KEY_CONNECTED.equals(configService.getServerStatus())) {
                System.out.println(properties.getProperty("namespace"));
                return true;
            }
            try {
                Thread.sleep(CONFIG.getConnectTimeout());
                LOGGER.info(String.format(Locale.ROOT, "The %s times to retry to connect to nacos", tryNum));
            } catch (InterruptedException e) {
                // ignored
            }
        }
        if (KEY_CONNECTED.equals(configService.getServerStatus())) {
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        System.out.println("closed");
        try {
            configService.shutDown();
        } catch (NacosException e) {
            LOGGER.warning("Nacos close exception occurs. ");
        }
    }
}