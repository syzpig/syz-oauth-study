package com.syz.security.zuul.rpc;

import com.syz.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "admin-auth", path = "client")  //这里先不上熔断，因为为测试打印日志
public interface ClientAuthRPC {
    /**
     * 网关进行服务鉴权，首先去授权服务获取token
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ObjectRestResponse getAccessToken(@RequestParam("clinetId") String clinetId, @RequestParam("sceret") String sceret);
}
