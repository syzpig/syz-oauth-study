package com.syz.security.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
/*@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients*/
@ComponentScan("com.syz.security.zuul")
public class AdminZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminZuulApplication.class, args);
	}
}
