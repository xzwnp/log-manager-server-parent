package com.example.xiao.logmanager.server.query.center;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;


public class TongYiApiTest {
    public static void callWithMessage()
            throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
        String content = "修复以下报错:\n" +
                "Application run failed\n" +
                "org.springframework.context.ApplicationContextException: Failed to start bean 'webServerStartStop'\n" +
                "at org.springframework.context.support.DefaultLifecycleProcessor.doStart(DefaultLifecycleProcessor.java:287)\n" +
                "at org.springframework.context.support.DefaultLifecycleProcessor$LifecycleGroup.start(DefaultLifecycleProcessor.java:467)\n" +
                "at java.base/java.lang.Iterable.forEach(Iterable.java:75)\n" +
                "at org.springframework.context.support.DefaultLifecycleProcessor.startBeans(DefaultLifecycleProcessor.java:256)\n" +
                "at org.springframework.context.support.DefaultLifecycleProcessor.onRefresh(DefaultLifecycleProcessor.java:201)\n" +
                "at org.springframework.context.support.AbstractApplicationContext.finishRefresh(AbstractApplicationContext.java:978)\n" +
                "at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:627)\n" +
                "at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146)\n" +
                "at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754)\n" +
                "at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:456)\n" +
                "at org.springframework.boot.SpringApplication.run(SpringApplication.java:334)\n" +
                "at org.springframework.boot.SpringApplication.run(SpringApplication.java:1354)\n" +
                "at org.springframework.boot.SpringApplication.run(SpringApplication.java:1343)\n" +
                "at com.example.xiao.logmanager.server.user.SystemAdminApplication.main(SystemAdminApplication.java:12)\n" +
                "Caused by: java.lang.reflect.UndeclaredThrowableException: null\n" +
                "at org.springframework.util.ReflectionUtils.rethrowRuntimeException(ReflectionUtils.java:147)\n" +
                "at com.alibaba.cloud.nacos.registry.NacosServiceRegistry.register(NacosServiceRegistry.java:83)\n" +
                "at org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration.register(AbstractAutoServiceRegistration.java:264)\n" +
                "at com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration.register(NacosAutoServiceRegistration.java:78)\n" +
                "at org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration.start(AbstractAutoServiceRegistration.java:156)\n" +
                "at org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration.onApplicationEvent(AbstractAutoServiceRegistration.java:119)\n" +
                "at org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration.onApplicationEvent(AbstractAutoServiceRegistration.java:49)\n" +
                "at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:178)\n" +
                "at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:171)\n" +
                "at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:149)\n" +
                "at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:451)\n" +
                "at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:384)\n" +
                "at org.springframework.boot.web.servlet.context.WebServerStartStopLifecycle.start(WebServerStartStopLifecycle.java:47)\n" +
                "at org.springframework.context.support.DefaultLifecycleProcessor.doStart(DefaultLifecycleProcessor.java:284)\n" +
                "... 13 common frames omitted\n" +
                "Caused by: com.alibaba.nacos.api.exception.NacosException: Client not connected, current status:STARTING\n" +
                "at com.alibaba.nacos.common.remote.client.RpcClient.request(RpcClient.java:643)\n" +
                "at com.alibaba.nacos.common.remote.client.RpcClient.request(RpcClient.java:623)\n" +
                "at com.alibaba.nacos.client.naming.remote.gprc.NamingGrpcClientProxy.requestToServer(NamingGrpcClientProxy.java:357)\n" +
                "at com.alibaba.nacos.client.naming.remote.gprc.NamingGrpcClientProxy.doRegisterService(NamingGrpcClientProxy.java:210)\n" +
                "at com.alibaba.nacos.client.naming.remote.gprc.NamingGrpcClientProxy.registerService(NamingGrpcClientProxy.java:124)\n" +
                "at com.alibaba.nacos.client.naming.remote.NamingClientProxyDelegate.registerService(NamingClientProxyDelegate.java:98)\n" +
                "at com.alibaba.nacos.client.naming.NacosNamingService.registerInstance(NacosNamingService.java:143)\n" +
                "at com.alibaba.cloud.nacos.registry.NacosServiceRegistry.register(NacosServiceRegistry.java:75)\n" +
                "... 25 common frames omitted";
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
        msgManager.add(userMsg);
        QwenParam param =
                QwenParam.builder().apiKey("sk-ab924789146e48619ee5f5755cbdf90b").model(Generation.Models.QWEN_TURBO).messages(msgManager.get())
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .build();
        GenerationResult result = gen.call(param);
        System.out.println(result);
    }


    public static void main(String[] args) {
        try {
            callWithMessage();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }
}