package com.syz.security.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient //添加让服务发现服务器,使用服务器.Spring cloud 实现服务发现自启动； 让服务使用eureka服务器，只需添加@EnableDiscoveryClient注解就可以了
/*
@EnableEurekaClient注解入口分析，分析主要调用链中的类和方法。
通过@EnableEurekaClient这个简单的注解，在spring cloud应用启动的时候，就可以把EurekaDiscoveryClient注入，继而使用NetFlix提供的Eureka client。
spring cloud中discovery service有许多种实现（eureka、consul、zookeeper等等），@EnableDiscoveryClient基于spring-cloud-commons, @EnableEurekaClient基于spring-cloud-netflix。
其实用更简单的话来说，就是如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient。
注解@EnableEurekaClient上有@EnableDiscoveryClient注解，可以说基本就是EnableEurekaClient有@EnableDiscoveryClient的功能，另外上面的注释中提到，其实@EnableEurekaClientz注解就是一种方便使用eureka的注解而已，可以说使用其他的注册中心后，都可以使用@EnableDiscoveryClient注解，但是使用@EnableEurekaClient的情景，就是在服务采用eureka作为注册中心的时候，使用场景较为单一。
*/
@EnableEurekaClient
//引入网关服务
@EnableZuulProxy
public class AdminGateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminGateApplication.class, args);
	}
}
