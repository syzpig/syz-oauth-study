package com.syz.security.auth.mapper;

import com.syz.security.auth.entity.Client;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ClientMapper extends Mapper<Client> {
    //获取拥有授权所有客户端 就是那些可以访问的客户端
    //tkMapper用法
    @Select(" SELECT client.code from gate_client client\n" +
            "    inner join gate_client_service gcs on gcs.client_id=client.id\n" +
            "    where gcs.service_id=#{serviceId}")
    @ResultType(String.class)
    List<String> selectAllowedClient(String serviceId);
}