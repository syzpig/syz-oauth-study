package com.syz.security.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient  //用于发现eureka客户端
//@EnableDiscoveryClient  //让服务使用eureka服务器  使用任意一个即可，建议使用这个
@MapperScan("com.syz.security.user.mapper")
@ComponentScan("com.syz.security.user")
@EnableTransactionManagement
public class AdminUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminUserApplication.class, args);
	}
}
