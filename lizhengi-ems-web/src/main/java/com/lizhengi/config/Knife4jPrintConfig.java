package com.lizhengi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 在终端打印 Knife4j 接口文档地址
 *
 * @author 栗筝i
 */
@Component
@Slf4j
public class Knife4jPrintConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            // 获取IP
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            // 获取端口号
            int port = event.getWebServer().getPort();
            // 获取应用名
            String applicationName = event.getApplicationContext().getApplicationName();
            // 打印 swagger 文档地址
            log.info("项目启动启动成功！swagger3 接口文档地址: http://" + hostAddress + ":" + port + applicationName + "/swagger-ui/index.html");
            // 打印 swagger2 文档地址
            log.info("项目启动启动成功！knife4j 接口文档地址: http://" + hostAddress + ":" + port + applicationName + "/doc.html");
        } catch (UnknownHostException e) {
            log.error("打印 swagger3 & knife4j 文档地址失败");
        }
    }
}
