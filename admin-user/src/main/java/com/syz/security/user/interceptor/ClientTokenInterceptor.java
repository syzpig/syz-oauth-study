package com.syz.security.user.interceptor;


import com.syz.security.common.exception.auth.ClientForbbionException;
import com.syz.security.common.msg.BaseResponse;
import com.syz.security.common.msg.ObjectRestResponse;
import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.user.config.WebConfig;
import com.syz.security.user.jwt.ClientTokenUtil;
import com.syz.security.user.rpc.ClientAuthRPC;
import feign.RequestInterceptor;
import feign.RequestTemplate;
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
 * 该拦截器只是举例： 通过fegin方式获取token验证  就是在每次进行fegin远程调用（@FeignClient）的时，就会走这个拦截器
 *
 * 该类一般是放在common公共类中，够其他服务使用
 */
@Configuration
public class ClientTokenInterceptor implements RequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(ClientTokenInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("发起fegin请求前，申请token");
        requestTemplate.header("client-token","token");  //把申到的token扔到这个桶里
    }
}
