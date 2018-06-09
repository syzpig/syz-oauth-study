package com.syz.security.user.interceptor;

import com.syz.security.common.context.BaseContextHandler;
import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.user.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  创建mvc拦截器 初始化BaseContextHandler用户信息，
 *
 *  一般情况下，对来自浏览器的请求的拦截，是利用Filter实现的，这种方式可以实现Bean预处理、后处理。
 Spring MVC的拦截器不仅可实现Filter的所有功能，还可以更精确的控制拦截精度。
 Spring为我们提供了org.springframework.web.servlet.handler.HandlerInterceptorAdapter这个适配器，继承此类，可以非常方便的实现自己的拦截器
 */
@Configuration
public class JWTInterceptor extends HandlerInterceptorAdapter{
    @Value("${jwt.token-header.jwtTokenHeader}")
    private String jwtTokenHeader;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //获取用户请求的token
        String token = request.getHeader(jwtTokenHeader);
        //公钥验证用户token ,并解析token，获取用户信息
        IJWTInfo infoFromToken = jwtUtil.getInfoFromToken(token);
        //把用户信息放入全局当前操作信息类中，供服务使用，在用户退出时清空
        BaseContextHandler.setUsername(infoFromToken.getUserName());
        BaseContextHandler.setUsername(infoFromToken.getName());
        BaseContextHandler.setUserID(infoFromToken.getUserId());
        return super.preHandle(request,response,handler);
    }
}
