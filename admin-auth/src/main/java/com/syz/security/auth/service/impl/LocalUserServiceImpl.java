package com.syz.security.auth.service.impl;

import com.syz.security.auth.bean.UserInfo;
import com.syz.security.auth.service.LocalUserService;
import com.syz.security.common.exception.auth.UserInvalidException;
import org.springframework.stereotype.Service;

@Service
public class LocalUserServiceImpl implements LocalUserService {

    /**
     *模拟用户登陆验证
     */
    @Override
    public UserInfo login(String userName, String password) {
        //此处可以变为从admin-user获取  就是用户服务
        UserInfo userInfo = new UserInfo(userName,"管理员","1","123456");
        if(!userInfo.getPassword().equals(password)){
            throw new UserInvalidException("user password error!");
        }
        return userInfo;
    }
}
