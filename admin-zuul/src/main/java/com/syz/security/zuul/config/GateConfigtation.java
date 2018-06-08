package com.syz.security.zuul.config;

import com.syz.security.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *初始化全局异常对象
 */
@Configuration  //添加psring扫描注解
public class GateConfigtation {
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
   /* 一个异常在其中流转的过程为：
    比如doLogin方法抛出了自定义异常，其code为：FAIL，message为：用户名或密码错误，
    由于在controller的方法中没有捕获这个异常，所以会将异常抛给GlobalExceptionHandler，
    然后GlobalExceptionHandler通过WebResult将状态码和提示信息返回给前端，前端通过默认的处理函数，
    弹框提示用户“用户名或密码错误”。而对于这样的一次交互，我们根本不用编写异常处理部分的逻辑。
   */
}
