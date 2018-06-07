package com.syz.security.common.util.jwt;

import com.syz.security.common.constant.CommonConstants;
import com.syz.security.common.util.KeyHelper;
import com.syz.security.common.util.StringHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

/**
 * 这两个方法任何后端都可以调用，因此，给他提到公共类中
 */
public class JWTHelpr {
    /**
     * 秘钥加密token   用于给网关服务用，加密   因为加密公钥在网管那里
     */
    //name/account(username)/userId/expire  token中可以放这些东西
    public static String generateeToken(IJWTInfo ijwtInfo, String priKeyPath, int expireTime) throws Exception {//用于生成token
        String compactJws = Jwts.builder()
                .setSubject(ijwtInfo.getUserName())   //subject可用于定义用户名
                .claim(CommonConstants.JWT_KEY_USER_ID, ijwtInfo.getUserId())  //claim用于自定义自己的key
                .claim(CommonConstants.JWT_KEY_NAME, ijwtInfo.getName())
                .setExpiration(DateTime.now().plusSeconds(expireTime).toDate())
                .signWith(SignatureAlgorithm.HS512, KeyHelper.getPrivateKey(priKeyPath))  //这里的key使用公钥和秘钥
                .compact();
        return compactJws;
        /*我们正在构建一个将注册索赔 sub（主题）设置为的JWT Joe。我们使用SHA-512算法使用HMAC签署JWT。最后，我们正在将它压缩成其String形式。*/
    }

    /**
     * 公钥解析token   用于给后台服务用  去解密  获取token中的用户信息  后端服务只用这个就可以了  用于后端服务  因为后端拿到公钥皆可以解密获取用户信息
     * <p>
     * 解析token，为了获取上面传递的信息name/account(username)/userId/expire
     * 但是如果签名验证失败呢？你可以捕捉SignatureException并做出相应的反应
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token);//好的，我们可以信任这个JWT
        return claimsJws;

    }

    /**
     * 获取token中的用户信息
     */
    public static IJWTInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), StringHelper.getObjectValue(CommonConstants.JWT_KEY_USER_ID), StringHelper.getObjectValue(CommonConstants.JWT_KEY_NAME));

    }

}
