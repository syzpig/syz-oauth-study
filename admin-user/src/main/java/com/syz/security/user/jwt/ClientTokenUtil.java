package com.syz.security.user.jwt;


import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.common.util.jwt.JWTHelpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 服务鉴权使用工具类
 */
@Configuration
public class ClientTokenUtil {
    private Logger logger = LoggerFactory.getLogger(ClientTokenUtil.class);


    @Value("${client.pub-key.path.pubKeyPath}")
    private String pubKeyPath;//获取公钥

    /**
     * 这个方法 后端服务只用这个就可以了  用于后端服务
     */
    public IJWTInfo getInfoFromToken(String token) throws Exception {
      return JWTHelpr.getInfoFromToken(token,pubKeyPath);
    }


}
