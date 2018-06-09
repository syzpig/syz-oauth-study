package com.syz.security.user.controller;

import com.syz.security.common.context.BaseContextHandler;
import com.syz.security.common.rest.BaseController;
import com.syz.security.user.entity.BaseUser;
import com.syz.security.user.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * jwt验证后与后台结合，通过MVC拦截器(实现初始化)+BaseContext(类似threadLocal)两部分去实现
 */
@RestController
@RequestMapping("user")
public class UserCtl extends BaseController<BaseUserService,BaseUser>{
 //例如在网关过滤器验证用户有效性后，去获取当前用户是谁，
    @RequestMapping("who")
    public String getCurrentUser(){
       return BaseContextHandler.getUsername();//通过mvc拦截器去初始化BaseContextHandler象
    }
}
