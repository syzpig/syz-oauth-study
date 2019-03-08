package com.syz.security.transaction.servicea.servicea.transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * 这个类就是用来调用netty来发送和读取消息的，控制事务，读取信号量
 * 相当于工具类
 */
public class SYZTransactionalManager {

    //定义一个nettiClinet客户端
    private static NettyClient nettyClient;  //用静态的原因是因为方法是静态的

    //这个客户端我们可以使用注入方式，但是是set注入
    @Autowired   //在这里注入，是因为对象传进来，赋给这个属性
    public static void setNettyClient(NettyClient nettyClient) {
        SYZTransactionalManager.nettyClient = nettyClient;
    }

    //第一个方法是我们创建事务组的方法
    public static String createSYZTransactionGroup() {
        //则返回一个groupID
        String gourpId = UUID.randomUUID().toString();

        //只是返回gourpID肯定还是不行的，你并没有真正的去创建
        //所以在返回之前，你要发送数据  通过nettyClinet

        //先创建事务组
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", gourpId);//吧事务id传过去
        jsonObject.put("command", "create");//动作

        nettyClient.send(jsonObject);//通过这个去发送数据
        return gourpId;
    }

    //创建一个添加事务组的方法  参数一代表：你要创建的事务，参数二：你要添加到那个groupID中(这个参数在syzTransactional已经有了可以先不写)
    public static SYZTransactional addSYZTransactionGroup(SYZTransactional syzTransactional, boolean isEnd,TransactionType transactionType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", syzTransactional.getGroupId());//添加事务首先先把groupID取出来，
        jsonObject.put("transaction", syzTransactional.getTransactionId());//指定当前是哪个事务组
        jsonObject.put("transactionType", transactionType);//
        jsonObject.put("isEnd", isEnd);//添加事务要告诉他是否是已结束 是否是结束的标志

        nettyClient.send(jsonObject);//通过这个去发送数据
        return syzTransactional;
    }

    /**
     * 创建我们的事务方法
     */
    public static SYZTransactional createSyzTransaction(String groupId) {
        String transactionId=UUID.randomUUID().toString();
        SYZTransactional syzTransactional = new SYZTransactional(groupId,transactionId);
    }
}
