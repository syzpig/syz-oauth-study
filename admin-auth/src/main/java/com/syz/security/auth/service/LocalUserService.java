package com.syz.security.auth.service;


import com.syz.security.auth.bean.UserInfo;

public interface LocalUserService {
  UserInfo login(String userName, String password);
}
