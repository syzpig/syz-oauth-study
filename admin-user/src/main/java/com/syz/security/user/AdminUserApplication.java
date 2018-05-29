package com.syz.security.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //用于发现eureka客户端
@EnableDiscoveryClient  //让服务使用eureka服务器
public class AdminUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminUserApplication.class, args);
	}
}
