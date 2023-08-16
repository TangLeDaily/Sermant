/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.huaweicloud.integration.monitor;

import com.huaweicloud.integration.common.MetricEnum;
import com.huaweicloud.integration.utils.RequestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/**
 * 监控测试类
 *
 * @author ZHP
 * @since 2002-11-24
 */
@EnabledIfEnvironmentVariable(named = "TEST_TYPE", matches = "monitor")
public class MonitorTest {
    /**
     * 监控采集URL
     */
    private static final String URL = "http://127.0.0.1:12345/";

    private static final String REQ_URL = "http://127.0.0.1:28050/consumer/flow/cirEx";

    private static final String QPS = "qps";

    private static final String TPS = "tps";

    private static final List<String> JDK8_MONOPOLIZE_INDEX_LIST = Arrays.asList("code_cache_init", "code_cache_max",
            "code_cache_used", "code_cache_committed");

    private static final List<String> JDK9PLUS_MONOPOLIZE_INDEX_LIST = Arrays.asList("non_nmethods_init",
            "non_nmethods_max",
            "non_nmethods_used", "non_nmethods_committed",
            "profiled_nmethods_init", "profiled_nmethods_max",
            "profiled_nmethods_used", "profiled_nmethods_committed",
            "non_profiled_nmethods_init", "non_profiled_nmethods_max",
            "non_profiled_nmethods_used", "non_profiled_nmethods_committed",
            "epsilon_heap_init", "epsilon_heap_max",
            "epsilon_heap_used", "epsilon_heap_committed");

    @Test
    public void testMonitor() {
        String string = RequestUtils.get(URL, new HashMap<>(), String.class);
        Assert.notNull(string, "指标信息查询失败");
        String[] metrics = string.split("\n");
        Map<String, Double> map = new HashMap<>();
        for (String metric : metrics) {
            if (metric.startsWith("#")) {
                continue;
            }
            String[] data = metric.split(" ");
            if (data.length >= 2) {
                map.put(data[0], Double.parseDouble(data[1]));
            }
        }
        Assert.notEmpty(map, "解析响应结果获取指标信息失败");
        for (MetricEnum metricEnum : MetricEnum.values()) {
            String metricEnumName = metricEnum.getName();
            // 当JDK大于8时，JDK8独有的指标则忽略
            if (javaVersionGreaterThanJDK8() && JDK8_MONOPOLIZE_INDEX_LIST.contains(metricEnumName)) {
                continue;
            }
            // 当JDK小于等于8时，JDK9及以上版本独有的指标则忽略
            if (!javaVersionGreaterThanJDK8() && JDK9PLUS_MONOPOLIZE_INDEX_LIST.contains(metricEnumName)) {
                continue;
            }
            Assert.isTrue(map.containsKey(metricEnumName), "缺少指标信息" + metricEnumName);
        }
    }

    private boolean javaVersionGreaterThanJDK8() {
        String javaVersion[] = System.getProperty("java.version").split("\\.");
        return Integer.valueOf(javaVersion[0]) >= 9;
    }

    @Test
    public void testFlowControlMonitor() {
        String res = RequestUtils.get(REQ_URL, new HashMap<>(), String.class);
        Assert.notNull(res, "熔断指标前置请求失败");
        String string = RequestUtils.get(URL, new HashMap<>(), String.class);
        Assert.notNull(string, "熔断指标信息查询失败");
        String[] metrics = string.split("\n");
        boolean qpsFlag = false;
        boolean tpsFlag = false;
        for (String metric : metrics) {
            if (metric.startsWith("#")) {
                continue;
            }
            String[] data = metric.split(" ");
            if (data[0].startsWith(QPS)) {
                qpsFlag = true;
            }
            if (data[0].startsWith(TPS)) {
                tpsFlag = true;
            }
        }
        Assert.isTrue(qpsFlag, "缺少qps指标信息");
        Assert.isTrue(tpsFlag, "缺少tps指标信息");
    }
}
