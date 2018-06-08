package com.syz.security.zuul.rest;

import com.syz.security.zuul.bean.UserInfo;
import com.syz.security.zuul.exception.UserInvalidException;
import com.syz.security.zuul.jwt.JWTUtil;
import com.syz.security.zuul.service.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jwt")
public class JWTCtl {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private LocalUserService localUserService;

    /**
     * 验证用户 是否合法，合法则给其生成token
     */
    @RequestMapping("token")
    public String testjwt(String username, String password) throws Exception {
        //1.验证账户合法性
        UserInfo userInfo = localUserService.login(username, password);
        //2.合法则生成token
        return jwtUtil.generateeToken(userInfo);
        //3.否则返回status 402

    }
}
