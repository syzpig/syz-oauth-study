package com.syz.security.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/*原因是：springboot启动时会自动注入数据源和配置jpa
		解决：在@SpringBootApplication中排除其注入
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})*/
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableZuulProxy
@EnableEurekaClient
@ComponentScan("com.syz.security.zuul")
@EnableFeignClients
public class AdminZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminZuulApplication.class, args);
	}
}
