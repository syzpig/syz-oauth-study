package com.syz.security.auth.service.impl;


import com.syz.security.auth.bean.ClientInfo;
import com.syz.security.auth.entity.Client;
import com.syz.security.auth.exception.ClientInvalidException;
import com.syz.security.auth.mapper.ClientMapper;
import com.syz.security.auth.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *进行验证，验证身份的有效性
 */
@Service
public class DBClientServiceImpl implements ClientService{

  @Autowired
  private ClientMapper clientMapper;
  //这一步其实就是派发token的过程
  @Override
  public ClientInfo apply(String clientId, String secret) {
    Client client = new Client();
    client.setCode(clientId);
    client= clientMapper.selectOne(client);
    if(client==null || !client.getSecret().equals(secret)){
      throw new ClientInvalidException("Client not found or Client secret is error");
    }
    return new ClientInfo(client.getCode(),client.getName(),client.getId().toString());
  }

  @Override
  public List<String> getAllowedClient(String clientId, String secret) {
    ClientInfo info = this.apply(clientId,secret);
    return clientMapper.selectAllowedClient(info.getId());
  }
}
