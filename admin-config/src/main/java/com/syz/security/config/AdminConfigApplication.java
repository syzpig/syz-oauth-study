package com.syz.security.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableAutoConfiguration
@EnableConfigServer
@EnableDiscoveryClient
@EnableEurekaClient
public class AdminConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminConfigApplication.class, args);
	}
}
