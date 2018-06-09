package com.syz.security.auth.rest;

import com.syz.security.auth.bean.ClientInfo;
import com.syz.security.auth.service.ClientService;
import com.syz.security.auth.util.ClientTokenUtil;
import com.syz.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *服务鉴权
 */
@RestController
@RequestMapping("client")
public class ClientCtl {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientTokenUtil clientTokenUtil;
    /**
     *鉴权 一般是通过clientId客户端Id和secret秘钥两个参数进行验证  派发token
     */
    @RequestMapping(value = "token",method = RequestMethod.POST)
    public ObjectRestResponse getAccessToken(String clinetId,String sceret) throws Exception {
        ClientInfo apply = clientService.apply(clinetId,sceret);
        return new ObjectRestResponse<String>().result(clientTokenUtil.generateeToken(apply));
    }
    /**
     *获取拥有授权所有客户端 就是那些可以访问的客户端  也就是其他服务
     */
    @RequestMapping(value = "/myClinet")
    public List<String> getAllowedClient(String serviceId, String sceret){
        return clientService.getAllowedClient(serviceId,sceret);
    }
}
