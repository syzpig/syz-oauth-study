package com.syz.security.auth.service;


import com.syz.security.auth.bean.ClientInfo;
import com.syz.security.auth.bean.UserInfo;

import java.util.List;

/**
 *服务鉴权  一般是通过clientId客户端Id和secret秘钥两个参数进行验证
 */
public interface ClientService {
  //鉴权
  ClientInfo apply(String clientId, String secret);
  //获取拥有授权所有客户端 就是那些可以访问的客户端  也就是其他服务
  List<String> getAllowedClient(String serviceId, String secret);
}
