package com.syz.security.user.rpc;

import com.syz.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "admin-auth",path = "client")  //这里先不上熔断，因为为测试打印日志
public interface ClientAuthRPC {
    /**
     * 获取拥有授权所有客户端 就是那些可以访问的客户端  也就是其他服务
     */
    @RequestMapping(value = "/myClinet")
    public ObjectRestResponse<List<String>> getAllowedClient(@RequestParam("serviceId") String serviceId, @RequestParam("sceret") String sceret);
}
