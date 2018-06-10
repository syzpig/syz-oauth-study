package com.syz.security.user.interceptor;


import com.syz.security.common.exception.auth.ClientForbbionException;
import com.syz.security.common.msg.BaseResponse;
import com.syz.security.common.msg.ObjectRestResponse;
import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.user.jwt.ClientTokenUtil;
import com.syz.security.user.rpc.ClientAuthRPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 使用mvc拦截器创建客户端拦截器
 */
@Configuration
public class ClientInterceptor extends HandlerInterceptorAdapter{
    private Logger logger = LoggerFactory.getLogger(ClientInterceptor.class);
    @Value("${client.token-header.tokenHeader}")
    private String tokenHeader;
    @Autowired
    private ClientTokenUtil clientTokenUtil;
    @Autowired
    private ClientAuthRPC clientAuthRPC;//远程过程调用。获取可访问用户列表进行校验

    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    /**
     *合法就通过，不合法就抛异常  合法后还要通过fegin rpc远程验证你有没有授权，能不能访问我
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("start ClientInterceptor 服务认证拦截器...........");
        //获取用户请求的token
        String token = request.getHeader(tokenHeader);
        //公钥验证用户token ,并解析token，获取用户信息
        IJWTInfo infoFromToken = clientTokenUtil.getInfoFromToken(token);
        //验证成功后，要获取访问者是谁
        String uniqueName = infoFromToken.getUniqueName();//就是clientName(code)

        //事实上，下面这块代码是可以优化的，不等待请求过来就可以做定时获取刷新的，获取可授权列表，下面去for循环比较就好
        BaseResponse resp=clientAuthRPC.getAllowedClient(clientId,clientSecret);
        if(resp.getStatus()==200){
            ObjectRestResponse<List<String>> allowesClients = (ObjectRestResponse<List<String>>) resp;
            for (String client:allowesClients.getData()) {
                if (client.equals(uniqueName)){
                    return super.preHandle(request,response,handler);//如果拥有权限则通过
                }
            }
        }
        throw new ClientForbbionException("client is forbbion...该服务无权限访问");
    }






}
