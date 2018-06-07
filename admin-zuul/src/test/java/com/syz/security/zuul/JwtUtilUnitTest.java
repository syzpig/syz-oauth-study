package com.syz.security.zuul;

import com.syz.security.zuul.jwt.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * 测试JWTUtil
 */
public class JwtUtilUnitTest extends BaseUnitTest{
    @Autowired
    private JWTUtil util;
    @Test
    public void testJwt() throws Exception {
       /* String token = util.generateeToken();
        Jws<Claims> claimsJws = util.parserToken(token);
        assertEquals("admin",claimsJws.getBody().getSubject());*/
    }
}
