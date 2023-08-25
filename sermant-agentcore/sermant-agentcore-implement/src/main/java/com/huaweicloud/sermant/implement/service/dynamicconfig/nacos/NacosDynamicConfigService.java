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
import com.huaweicloud.sermant.core.plugin.config.ServiceMeta;
import com.huaweicloud.sermant.core.service.dynamicconfig.DynamicConfigService;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigListener;
import com.huaweicloud.sermant.core.service.dynamicconfig.config.DynamicConfig;
import com.huaweicloud.sermant.core.utils.AesUtil;
import com.huaweicloud.sermant.core.utils.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.client.auth.impl.process.HttpLoginProcessor;
import com.alibaba.nacos.client.naming.remote.http.NamingHttpClientManager;
import com.alibaba.nacos.plugin.auth.api.LoginIdentityContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * 动态配置服务，nacos实现
 *
 * @author tangle
 * @since 2023-08-17
 */
public class NacosDynamicConfigService extends DynamicConfigService {
    /**
     * 动态配置信息
     */
    protected static final DynamicConfig CONFIG = ConfigManager.getConfig(DynamicConfig.class);

    private static final ServiceMeta SERVICE_META = ConfigManager.getConfig(ServiceMeta.class);

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * 正则表达式，用于group名称合法化
     */
    private static Pattern pattern;

    /**
     * http的协议头
     */
    private static final String HTTP_PROTOCOL = "http://";

    /**
     * http请求获取所有配置项相关参数名
     */
    private static final String KEY_ACCESS_TOKEN = "accessToken";

    private static final String KEY_TOKEN_TTL = "tokenTtl";

    private static final String KEY_DATA_ID = "dataId";

    private static final String KEY_PAGE_ITEMS = "pageItems";

    private static final String KEY_GROUP = "group";

    private static final String KEY_SERVER = "server";

    /**
     * NacosListener的类型名称
     */
    private static final String TYPE_GROUP = "GROUP";

    private static final String TYPE_KEY = "KEY";

    /**
     * 定时更新监听器的线程池
     */
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    /**
     * 定时更新监听器的间隔时间
     */
    private static final long UPDATE_TIME_INTERVAL = 200L;

    private NacosBufferedClient nacosClient;

    private List<NacosListener> listeners;

    /**
     * nacos的http请求安全认证token上次获取时间
     */
    private long lastRefreshTime;

    /**
     * nacos的http请求安全认证token的ttl
     */
    private long tokenTtl;

    /**
     * nacos的http请求安全认证token的刷新窗口
     */
    private final long tokenRefreshWindow = 3000L;

    /**
     * nacos的http请求安全认证token
     */
    private String lastToken;

    private final Executor defaultExecutor = Executors.newSingleThreadExecutor();


    /**
     * 构造函数：编译正则表达式、初始化List
     */
    public NacosDynamicConfigService() {
        String allowedChars = "[a-zA-Z.=&:\\-_]+";
        pattern = Pattern.compile(allowedChars);
        listeners = new ArrayList<>();
    }

    @Override
    public void start() {
        System.out.println(SERVICE_META.getProject()); // TODO
        if (CONFIG.isEnableAuth()) {
            nacosClient = new NacosBufferedClient(CONFIG.getServerAddress(), CONFIG.getTimeoutValue(),
                    SERVICE_META.getProject(), CONFIG.getUserName(), CONFIG.getPassword());
        } else {
            nacosClient = new NacosBufferedClient(CONFIG.getServerAddress(), CONFIG.getTimeoutValue(),
                    SERVICE_META.getProject());
        }
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(this::updateConfigListener, UPDATE_TIME_INTERVAL,
                UPDATE_TIME_INTERVAL,
                TimeUnit.MILLISECONDS);
        /*
                    case NACOS:
                service = new NacosDynamicConfigService();
                break;
         */
    }

    @Override
    public void stop() {
        nacosClient.close();
    }

    @Override
    public Optional<String> doGetConfig(String key, String group) {
        if (!checkGroupName(group)) {
            return Optional.empty();
        }
        return Optional.ofNullable(nacosClient.getConfig(key, reBuildGroup(group)));
    }

    @Override
    public boolean doPublishConfig(String key, String group, String content) {
        if (!checkGroupName(group)) {
            return false;
        }
        return nacosClient.publishConfig(key, reBuildGroup(group), content);
    }

    @Override
    public boolean doRemoveConfig(String key, String group) {
        if (!checkGroupName(group)) {
            return false;
        }
        return nacosClient.removeConfig(key, reBuildGroup(group));
    }

    @Override
    public boolean doAddConfigListener(String key, String group, DynamicConfigListener listener) {

        if (!checkGroupName(group)) {
            return false;
        }
        String alreadyCheckGroup = reBuildGroup(group);
        Listener listenerNacos = instantiateListener(key, alreadyCheckGroup, listener);
        boolean result = nacosClient.addListener(key, alreadyCheckGroup, listenerNacos);
        if (result) {
            Map<String, Listener> mp = new HashMap<>();
            mp.put(key, listenerNacos);
            listeners.add(new NacosListener(TYPE_KEY, alreadyCheckGroup, mp, listener));
        }
        LOGGER.log(Level.SEVERE, "addListener: key: "+key+" ,group: "+group+" result:"+result); //TODO
        for (NacosListener nacosListener:listeners){
            LOGGER.log(Level.SEVERE, nacosListener.getGroup()); //TODO
        }
        return result;
    }

    @Override
    public boolean doRemoveConfigListener(String key, String group) {
        if (!checkGroupName(group)) {
            return false;
        }
        String alreadyCheckGroup = reBuildGroup(group);
        List<NacosListener> listenerList = getListener(key, alreadyCheckGroup, TYPE_KEY);
        for (NacosListener nacosListener : listenerList) {
            nacosClient.removeListener(key, alreadyCheckGroup, nacosListener.getKeyListener().get(key));
            listeners.remove(nacosListener);
        }
        return true;
    }

    @Override
    public boolean doAddGroupListener(String group, DynamicConfigListener listener) {
        if (!checkGroupName(group)) {
            return false;
        }
        String alreadyCheckGroup = reBuildGroup(group);
        List<String> keys = doListKeysFromGroup(alreadyCheckGroup);
        Map<String, Listener> keyListenerMap = new HashMap<>();
        for (String key : keys) {
            Listener listenerNacos = instantiateListener(key, alreadyCheckGroup, listener);
            if (!nacosClient.addListener(key, alreadyCheckGroup, listenerNacos)) {
                return false;
            }
            keyListenerMap.put(key, listenerNacos);
        }
        listeners.add(new NacosListener(TYPE_GROUP, alreadyCheckGroup, keyListenerMap, listener));
        return true;
    }

    @Override
    public boolean doRemoveGroupListener(String group) {
        if (!checkGroupName(group)) {
            return false;
        }
        String alreadyCheckGroup = reBuildGroup(group);
        List<NacosListener> listenerList = getGroupListener(alreadyCheckGroup);
        for (NacosListener nacosListener : listenerList) {
            for (Map.Entry<String, Listener> entry : nacosListener.getKeyListener().entrySet()) {
                String key = entry.getKey();
                Listener listener = entry.getValue();
                nacosClient.removeListener(key, alreadyCheckGroup, listener);
            }
            listeners.remove(nacosListener);
        }
        return true;
    }

    @Override
    public List<String> doListKeysFromGroup(String group) {
        if (!checkGroupName(group)) {
            return Collections.emptyList();
        }
        String alreadyCheckGroup = reBuildGroup(group);
        List<String> resultList = getGroupKeys().get(alreadyCheckGroup);
        return CollectionUtils.isEmpty(resultList) ? Collections.emptyList() : resultList;
    }

    /**
     * 获取监听器
     *
     * @param key 配置名称
     * @param alreadyCheckGroup 配置所在组（已合法化名称）
     * @param type 监听器类型
     * @return 监听器列表
     */
    private List<NacosListener> getListener(String key, String alreadyCheckGroup, String type) {
        List<NacosListener> list = new ArrayList<>();
        for (NacosListener nacosListener : listeners) {
            if (!nacosListener.getType().equals(type) || !nacosListener.getGroup().equals(alreadyCheckGroup)
                    || !nacosListener.getKeyListener().containsKey(key)) {
                continue;
            }
            list.add(nacosListener);
        }
        return list;
    }

    /**
     * 实例化监听器
     *
     * @param key 配置名称
     * @param alreadyCheckGroup 配置所在组（已合法化名称）
     * @param listener 动态配置监听器
     * @return nacos监听器
     */
    private Listener instantiateListener(String key, String alreadyCheckGroup, DynamicConfigListener listener) {
        return new Listener() {

            @Override
            public Executor getExecutor() {
                System.out.println("getExecutor");
                return null;
            }

            @Override
            public void receiveConfigInfo(String content) {
                System.out.println("get the config, key: "+key+" group:"+alreadyCheckGroup+" content:"+content);
                LOGGER.log(Level.SEVERE, "get the config, key: "+key+" group:"+alreadyCheckGroup+" content:"+content); //TODO
                listener.process(DynamicConfigEvent.modifyEvent(key, alreadyCheckGroup, content));
            }
        };
    }

    /**
     * 获取组内所有监听器
     *
     * @param alreadyCheckGroup 配置所在组（已合法化名称）
     * @return nacos监听器列表
     */
    private List<NacosListener> getGroupListener(String alreadyCheckGroup) {
        List<NacosListener> list = new ArrayList<>();
        for (NacosListener nacosListener : listeners) {
            if (!nacosListener.getType().equals(TYPE_GROUP) || !nacosListener.getGroup().equals(alreadyCheckGroup)) {
                continue;
            }
            list.add(nacosListener);
        }
        return list;
    }

    /**
     * 定时更新组监听器
     */
    private void updateConfigListener() {
        Map<String, List<String>> groupKeys = getGroupKeys();
        for (NacosListener nacosListener : listeners) {
            if (!nacosListener.getType().equals(TYPE_GROUP)) {
                continue;
            }
            String group = nacosListener.getGroup();
            List<String> truthKeys = groupKeys.getOrDefault(group, Collections.emptyList());
            if (CollectionUtils.isEmpty(truthKeys)) {
                doRemoveGroupListener(group); // client有但nacos无，故删除组监听器
            }
            Map<String, Listener> map = nacosListener.getKeyListener();
            Map<String, Listener> newMap = new HashMap<>(); // 更新后的新监听器Map
            for (String key : truthKeys) {
                // 遍历nacos该组内所有key
                Listener listenerNacos;
                if (map.containsKey(key)) {
                    // client组监听器中包含该key
                    listenerNacos = map.get(key); // 将监听器转移至新监听器Map
                } else {
                    listenerNacos = instantiateListener(key, group, nacosListener.getDynamicConfigListener());
                    boolean result = nacosClient.addListener(key, group, listenerNacos);
                    if (!result) {
                        LOGGER.log(Level.WARNING, "Nacos add listener failed group is {0} and key is {1}. ",
                                new String[]{group, key});
                        break;
                    }
                }
                newMap.put(key, listenerNacos);
            }
            nacosListener.setKeyListener(newMap); // 替换监听器Map，完成增加和删除操作
        }
    }

    /**
     * 获取nacos所有group的所有key
     *
     * @return group和其所有的keys组成的Map
     */
    private Map<String, List<String>> getGroupKeys() {
        final String httpResult = doGet(buildUrl());
        if ("".equals(httpResult)) {
            return new HashMap<>();
        }
        Map<String, List<String>> mp = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(httpResult);
        JSONArray pageItems = jsonObject.getJSONArray(KEY_PAGE_ITEMS);

        for (int i = 0; i < pageItems.size(); i++) {
            JSONObject itemObject = pageItems.getJSONObject(i);
            String dataId = itemObject.getString(KEY_DATA_ID);
            String dataGroup = itemObject.getString(KEY_GROUP);
            List<String> dataIdList = mp.getOrDefault(dataGroup, new ArrayList<>());
            dataIdList.add(dataId);
            mp.put(dataGroup, dataIdList);
        }
        return mp;
    }

    /**
     * 构建nacos查询所有group和key的http请求的url
     *
     * @return url
     */
    private String buildUrl() {
        final StringBuilder requestUrl = new StringBuilder().append(HTTP_PROTOCOL);
        int pageSize = Integer.MAX_VALUE;
        requestUrl.append(CONFIG.getServerAddress())
                .append("/nacos/v1/cs/configs?dataId=&group=&appName=&config_tags=&pageNo=1&pageSize=")
                .append(pageSize)
                .append("&tenant=")
                .append(SERVICE_META.getProject())
                .append("&search=accurate");
        if (CONFIG.isEnableAuth()) {
            String accessToken = getToken();
            requestUrl.append("&accessToken=")
                    .append(accessToken)
                    .append("&username=")
                    .append(AesUtil.decrypt(CONFIG.getPrivateKey(), CONFIG.getUserName()).orElse(""));
        }
        return requestUrl.toString();
    }

    /**
     * 获取token
     *
     * @return token
     */
    private String getToken() {
        if ((System.currentTimeMillis() - lastRefreshTime) >= TimeUnit.SECONDS
                .toMillis(tokenTtl - tokenRefreshWindow)) {
            HttpLoginProcessor httpLoginProcessor = new HttpLoginProcessor(
                    NamingHttpClientManager.getInstance().getNacosRestTemplate());
            LoginIdentityContext loginIdentityContext = httpLoginProcessor.getResponse(getProperties());
            lastToken = loginIdentityContext.getParameter(KEY_ACCESS_TOKEN);
            tokenTtl = Long.parseLong(loginIdentityContext.getParameter(KEY_TOKEN_TTL));

            lastRefreshTime = System.currentTimeMillis();
        }
        return lastToken;
    }

    /**
     * http的get请求
     *
     * @param url http请求url
     * @return 响应体body
     */
    private String doGet(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONFIG.getTimeoutValue()) // 连接主机服务超时时间
                    .setConnectionRequestTimeout(CONFIG.getTimeoutValue()) // 请求超时时间
                    .setSocketTimeout(CONFIG.getTimeoutValue()) // 数据读取超时时间
                    .build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Nacos http request exception.");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Nacos http close exception.");
            }
        }
        return result;
    }

    /**
     * 构建token时需要的Properties
     *
     * @return Properties
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(KEY_SERVER, CONFIG.getServerAddress());
        properties.setProperty(PropertyKeyConst.USERNAME,
                AesUtil.decrypt(CONFIG.getPrivateKey(), CONFIG.getUserName()).orElse(""));
        properties.setProperty(PropertyKeyConst.PASSWORD,
                AesUtil.decrypt(CONFIG.getPrivateKey(), CONFIG.getPassword()).orElse(""));
        return properties;
    }

    /**
     * 检查group名称合法性
     *
     * @return 是否合法
     */
    private boolean checkGroupName(String group) {
        if (pattern.matcher(group).matches()) {
            return true;
        }
        LOGGER.log(Level.WARNING, "Nacos group naming error, group name: {0}", group);
        return false;
    }

    /**
     * 重新构建合法的group名称
     *
     * @return 合法化的group名称
     */
    private String reBuildGroup(String group) {
        return group.replace('=', ':').replace('&', '_');
    }
}
