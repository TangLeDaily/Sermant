package com.huaweicloud.agentcore.test.application.router;

import com.huaweicloud.agentcore.test.application.controller.TestController;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装的http服务端
 *
 * @author tangle
 * @since 2023-09-08
 */
public class ControllerHandler implements HttpHandler {
    private static final int REQUEST_SUCCESS_CODE = 200;

    private static final int REQUEST_NOT_FOUND_CODE = 404;

    private static final int REQUEST_FAILED_CODE = 500;

    private static final String REQUEST_URL_NOT_FOUND = "No such request path.";

    private static final String REQUEST_PARAMS_ERROR = "Request params error.";

    private final TestController testController;

    public ControllerHandler() {
        this.testController = new TestController();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 获取输出流
        OutputStream os = exchange.getResponseBody();
        String response = setResponse(exchange);
        int responseCode = 200;
        if (response.equals(REQUEST_URL_NOT_FOUND)){
            responseCode = REQUEST_NOT_FOUND_CODE;
        }
        if (response.equals(REQUEST_PARAMS_ERROR)){
            responseCode = REQUEST_FAILED_CODE;
        }

        // 构建响应消息
        exchange.sendResponseHeaders(responseCode, response.length());

        // 发送响应消息
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    /**
     * 根据请求url分配执行方法
     *
     * @param exchange
     * @return
     */
    private String setResponse(HttpExchange exchange) {
        switch (exchange.getRequestURI().getPath()) {
            case RouterPath.REQUEST_PATH_PING:
                return testController.ping();
            case RouterPath.REQUEST_PATH_DYNAMIC_CONFIG:
                return testController.testDynamicConfig();
            case RouterPath.REQUEST_PATH_INSTALL_PLUGIN:
                return testController.testInstallPlugin();
            case RouterPath.REQUEST_PATH_UNINSTALL_PLUGIN:
                return testController.testUninstallPlugin();
            case RouterPath.REQUEST_PATH_UNINSTALL_AGENT:
                return testController.testUninstallAgent();
            case RouterPath.REQUEST_PATH_REINSTALL_AGENT:
                return testController.testReInstallAgent();
            case RouterPath.REQUEST_PATH_VIRTUAL_MACHINE: {
                if (exchange.getRequestURI().getQuery() != null) {
                    Map<String, String> params = getParams(exchange.getRequestURI().getQuery());
                    return testController.testVirtualMachine(params);
                }
                return REQUEST_PARAMS_ERROR;
            }
            default:
                return REQUEST_URL_NOT_FOUND;
        }
    }

    private Map<String, String> getParams(String query) {
        String[] params = query.split("&");
        Map<String, String> paramsMap = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String paramName = keyValue[0];
                String paramValue = keyValue[1];

                paramsMap.put(paramName, paramValue);
                System.out.println("参数名: " + paramName + ", 参数值: " + paramValue);
            }
        }
        return paramsMap;
    }
}
