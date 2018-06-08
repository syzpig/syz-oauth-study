package com.syz.security.zuul.jwt;


import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.common.util.jwt.JWTHelpr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 他只需要读公钥
 */
@Component
public class JWTUtil {
    @Value("${jwt.pub-key.path.pubKeyPath}")
    private String pubKeyPath;//获取公钥

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JWTHelpr.getInfoFromToken(token,pubKeyPath);
    }

}
