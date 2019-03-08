package com.syz.security.transaction.servicea.servicea.transactional;

import com.syz.security.transaction.servicea.servicea.utils.Task;

/**
 * 这是我们自己创建的事务,自己定义的事务
 */
public class SYZTransactional {
    //那么这个事务中有什么东西呢？

    //事务的Id 也就是事务他本身的ID,代表这个失事务是分布式事务中的子事务或者分支事务，他是属于这个groupId的
    private String groupId;
    // 然后有一个自己的id,
    private String transactionId;
    //还有一个是事务的状态，可以利用枚举
    private TransactionType transactionType;

    private Task task;  //一个事务有一个任务，这个用来阻塞事务提交或回滚的

    public SYZTransactional(String groupId, String transactionId) {
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.task = new Task(); //每个事务初始化一个task

    }

    public String getGroupId() {
        return groupId;
    }

    public Task getTask() {
        return task;
    }



    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
