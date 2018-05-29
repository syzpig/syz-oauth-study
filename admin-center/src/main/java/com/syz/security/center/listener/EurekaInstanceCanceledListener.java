package com.syz.security.center.listener;


import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 用于监听eureka服务停机通知
 * 在一些业务场景中，当容器初始化完成之后，需要处理一些操作，比如一些数据的加载、初始化缓存、特定任务的注册等等。这个时候我们就可以使用Spring提供的ApplicationListener来进行操作。
 */
@Configuration
@EnableScheduling
public class EurekaInstanceCanceledListener implements ApplicationListener {
    private Logger log = LoggerFactory.getLogger(EurekaInstanceCanceledListener.class);
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        // EurekaInstanceCanceledEvent服务挂掉事件  挂掉时触发
        if (applicationEvent instanceof EurekaInstanceCanceledEvent) {
            //获取失效事件
            EurekaInstanceCanceledEvent event = (EurekaInstanceCanceledEvent) applicationEvent;
            // 获取当前Eureka实例中的节点信息
            PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
            Applications applications = registry.getApplications();
            // 遍历获取已注册节点中与当前失效节点ID一致的节点信息
            applications.getRegisteredApplications().forEach((registeredApplication) -> {
                registeredApplication.getInstances().forEach((instance) -> {
                    if (instance.getInstanceId().equals(event.getServerId())) {
                        log.info("服务：" + instance.getAppName() + " 挂啦。。。");
                        // // TODO: 2017/9/3 扩展消息提醒 邮件、手机短信、微信等
                    }
                });
            });


        }
        //注册事件
        if (applicationEvent instanceof EurekaInstanceRegisteredEvent) {
            EurekaInstanceRegisteredEvent event = (EurekaInstanceRegisteredEvent) applicationEvent;
            log.info("服务：" + event.getInstanceInfo().getAppName() + " 注册成功啦。。。");
        }
        //心跳事件
        if (applicationEvent instanceof EurekaInstanceRenewedEvent) {
            EurekaInstanceRenewedEvent event = (EurekaInstanceRenewedEvent) applicationEvent;
            log.info("心跳检测服务：" + event.getInstanceInfo().getAppName() + "。。");
        }
        //可用事件
        if (applicationEvent instanceof EurekaRegistryAvailableEvent) {
            log.info("服务 Aualiable。。");
        }

    }


}

