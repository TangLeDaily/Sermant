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

package com.huaweicloud.sermant.tag.transmission.interceptors;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;
import com.huaweicloud.sermant.core.plugin.config.PluginConfigManager;
import com.huaweicloud.sermant.core.utils.tag.TrafficTag;
import com.huaweicloud.sermant.core.utils.tag.TrafficUtils;
import com.huaweicloud.sermant.tag.transmission.config.TagTransmissionConfig;

import org.apache.rocketmq.common.protocol.header.SendMessageRequestHeader;

import java.util.List;
import java.util.Map;

/**
 * RocketMQ流量标签透传的生产者拦截器，支持RocketMQ4.8+
 *
 * @author tangle
 * @since 2023-07-20
 */
public class RocketmqProducerInterceptor extends AbstractInterceptor {
    /**
     * SendMessageRequestHeader在sendMessage方法中的参数下标
     */
    private static final int ARGUMENT_INDEX = 3;

    /**
     * SendMessageRequestHeader的Properties中键值对的连接符（ASCII值为1）
     */
    private static final char LINK_MARK = 1;

    /**
     * SendMessageRequestHeader的Properties中键值对的分隔符（ASCII值为2）
     */
    private static final char SPLIT_MARK = 2;

    private final TagTransmissionConfig tagTransmissionConfig;

    /**
     * 构造器
     */
    public RocketmqProducerInterceptor() {
        tagTransmissionConfig = PluginConfigManager.getPluginConfig(TagTransmissionConfig.class);
    }

    @Override
    public ExecuteContext before(ExecuteContext context) {
        if (!tagTransmissionConfig.isEnabled()) {
            return context;
        }

        TrafficTag trafficTag = TrafficUtils.getTrafficTag();
        if (trafficTag == null) {
            return context;
        }

        if (context.getArguments()[ARGUMENT_INDEX] instanceof SendMessageRequestHeader) {
            SendMessageRequestHeader header = (SendMessageRequestHeader) context.getArguments()[ARGUMENT_INDEX];
            String oldProperties = header.getProperties();
            String newProperties = this.insertTags2Properties(oldProperties, trafficTag);
            header.setProperties(newProperties);
        }
        return context;
    }

    /**
     * 将流量标签插入到properties字符串中 原始properties的格式形如：key1[SOH]value1[STX]key2[SOH]value2，其中[SOH]为ASCII=1的符号，[STX]为ASCII=2的符号
     *
     * @param oldProperties 原始properties
     * @param trafficTag 流量标签
     * @return String
     */
    private String insertTags2Properties(String oldProperties, TrafficTag trafficTag) {
        Map<String, List<String>> tags = trafficTag.getTag();
        StringBuilder newProperties = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : tags.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            newProperties.append(entry.getKey());
            newProperties.append(LINK_MARK);
            newProperties.append(entry.getValue().get(0));
            newProperties.append(SPLIT_MARK);
        }
        if (newProperties.length() == 0) {
            return oldProperties;
        }
        if (oldProperties.length() == 0) {
            newProperties.deleteCharAt(newProperties.length() - 1);
            return newProperties.toString();
        }
        return newProperties.append(oldProperties).toString();
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        return context;
    }
}
