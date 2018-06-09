package com.syz.security.user.config;

import com.syz.security.common.handler.GlobalExceptionHandler;
import com.syz.security.user.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *配置config启动异常类  @Configuration  根据全局异常类中@ControllerAdvice("com.syz.security")扫描路径
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    //注入全局拦截器，引入进来
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    //引入自定义的JWTInterceptor拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //指定对什么路径等拦截
        registry.addInterceptor(getJWTInterceptor()).addPathPatterns("/**");
        //这里还可以指定哪些不用拦截，可通过自定义注解，哪些注解不拦截，或拦截等等
        super.addInterceptors(registry);
    }
    //初始化JWTInterceptor拦截器
    @Bean
    JWTInterceptor getJWTInterceptor(){
       return new JWTInterceptor();
    }













}
