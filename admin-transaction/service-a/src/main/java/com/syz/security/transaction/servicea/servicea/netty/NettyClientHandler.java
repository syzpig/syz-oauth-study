package com.syz.security.transaction.servicea.servicea.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.syz.security.transaction.servicea.servicea.transactional.SYZTransactional;
import com.syz.security.transaction.servicea.servicea.transactional.SYZTransactionalManager;
import com.syz.security.transaction.servicea.servicea.transactional.TransactionType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作为事务管理者，他需要：
 * 1、创建并保存事务组
 * 2、保存各个事务在对应的事务组内
 * 3、统计并判断事务组内的各个子事务状态，以算出当前事务组的状态（提交或回滚）
 * 4、通知各个子事务提交或回滚
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //事务组中的事务状态列表
    private static Map<String, List<String>> transactionTypeMap = new HashMap<>();
    //事务组是否已经接收到结束的标记
    private static Map<String, Boolean> isEndMap = new HashMap<>();
    //事务组中应该有的事务个数
    private static Map<String, Integer> transactionCountMap = new HashMap<>();

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(ctx.channel());
    }

    /**
     * 读数据
     */
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收数据：" + msg.toString());

        JSONObject jsonObject = JSON.parseObject(msg.toString());
        String command = jsonObject.getString("command");//create-创建事务组，add-添加事务组
        String groupId = jsonObject.getString("groupId");//事务组id
        //当事务组发现消息过来，我就要去找本地有哪些事务是属于这个事务组的，所以事务管理类里面还需要一个方法
        List<SYZTransactional> syzTransactionals = SYZTransactionalManager.getByGroupId(groupId);
        //拿到事务之后，就可以循环事务修改事务的状态
        for (SYZTransactional syzTransactional : syzTransactionals) {
            if ("rollback".equals(syzTransactional)) {
                syzTransactional.setTransactionType(TransactionType.rollback);
            } else {
                syzTransactional.setTransactionType(TransactionType.commit);
            }
            syzTransactional.getTask().signalTask();//设置完之后，唤醒当前阻塞  一唤醒对应逻辑就通了
        }
    }

   /**
    *发数据方法
    */

}
