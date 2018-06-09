package com.syz.security.auth.util;


import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.common.util.jwt.JWTHelpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 创建一个秘钥  用户授权使用的
 */
@Configuration
public class JWTUtil {
    private Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    //这个自定义key不使用，使用公钥秘钥
    /*@Value("${jwt.key}")
    private String key;*/
    @Value("${jwt.expireTime}")
    private int expireTime;  //时长
    @Value("${jwt.pub-key.path.pubKeyPath}")
    private String pubKeyPath;//获取公钥
    @Value("${jwt.pri-key.path.priKeyPath}")
    private String priKeyPath;//获取秘钥

    //name/account(username)/userId/expire  token中可以放这些东西
    //IJWTInfo实际网关不一定传这个类，会根据自己定义对象传进来  这个是给网关用
    public String generateeToken(IJWTInfo ijwtInfo) throws Exception {//用于生成token
        return JWTHelpr.generateeToken(ijwtInfo, priKeyPath, expireTime);
        /*我们正在构建一个将注册索赔 sub（主题）设置为的JWT Joe。我们使用SHA-512算法使用HMAC签署JWT。最后，我们正在将它压缩成其String形式。*/
    }

    /**
     * 这个方法 后端服务只用这个就可以了  用于后端服务
     */
    public IJWTInfo getInfoFromToken(String token) throws Exception {
      return JWTHelpr.getInfoFromToken(token,pubKeyPath);
    }


}
