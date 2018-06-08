package com.syz.security.zuul;

import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.common.util.jwt.JWTInfo;
import com.syz.security.zuul.jwt.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * 测试JWTUtil
 */
public class JwtUtilUnitTest extends BaseUnitTest {
    @Autowired
    private JWTUtil util;

    @Test
    public void testJwt() throws Exception {
        String username = "admin";
        String userId = "1";
        String name = "syz";
        String token = util.generateeToken(new JWTInfo(username, userId, name));
        IJWTInfo infoFromToken = util.getInfoFromToken(token);
        Thread.sleep(3000);
        assertEquals(infoFromToken.getUserName(), username);
    }


}
