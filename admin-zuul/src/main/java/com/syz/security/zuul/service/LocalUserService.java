package com.syz.security.zuul.service;

import com.syz.security.zuul.bean.UserInfo;

public interface LocalUserService {
  UserInfo login(String userName,String password);
}
