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

package com.huaweicloud.sermant.tag.transmission.listener;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.plugin.config.PluginConfigManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEventType;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigListener;
import com.huaweicloud.sermant.tag.transmission.config.TagTransmissionConfig;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 流量标签动态配置监听器
 *
 * @author lilai
 * @since 2023-07-20
 */
public class TagConfigListener implements DynamicConfigListener {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private TagTransmissionConfig tagTransmissionConfig;

    private final Yaml yaml;

    /**
     * 构造方法
     */
    public TagConfigListener() {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        this.yaml = new Yaml(representer);
        this.tagTransmissionConfig = PluginConfigManager.getPluginConfig(TagTransmissionConfig.class);
    }

    @Override
    public void process(DynamicConfigEvent event) {
        LOGGER.log(Level.INFO, "event"+event.getKey()+" / "+event.getGroup()+" /:"+event.getContent());
        if (event.getEventType() == DynamicConfigEventType.DELETE) {
            tagTransmissionConfig.setEnabled(false);
            return;
        }
        try {
            updateConfig(event);
        } catch (YAMLException e) {
            LOGGER.severe(String.format(Locale.ROOT, "Fail to convert dynamic tag config, %s", e.getMessage()));
        }
    }

    private void updateConfig(DynamicConfigEvent event) {
        TagTransmissionConfig dynamicConfig = yaml.loadAs(event.getContent(), TagTransmissionConfig.class);
        if (dynamicConfig == null) {
            return;
        }
        tagTransmissionConfig.setEnabled(dynamicConfig.isEnabled());
        tagTransmissionConfig.setTagKeys(dynamicConfig.getTagKeys());
        LOGGER.info(String.format(Locale.ROOT, "Update tagTransmissionConfig, %s",
                tagTransmissionConfig.toString()));
    }
}
