package com.syz.security.transaction.servicea.servicea.aspect;

import com.syz.security.transaction.servicea.servicea.annotation.SYZTransaction;
import com.syz.security.transaction.servicea.servicea.transactional.SYZTransactional;
import com.syz.security.transaction.servicea.servicea.transactional.SYZTransactionalManager;
import com.syz.security.transaction.servicea.servicea.transactional.TransactionType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 4.还需要与事务注解对应的一个切面
 */

@Aspect
@Component
public class SYZTransactionAspect implements Ordered {//Ordered实现他是为了设置切面的优先级，因为我们的方法有多个注解，也就是多个切面
    //那么事务的开始定义好了之后，我们切面该怎么操作呢？

    //我们切面就切我们注解
    @Around("@annotation(com.syz.security.transaction.servicea.servicea.annotation.SYZTransaction)")
    public void invoke(ProceedingJoinPoint point) {
        //加好切点之后，那么我们第一步，就去看我们的注解上面时候有isStart，并且等于true的话
        //那么我们就调用去创建事务租的方法
        //所以第一步先拿到我们的注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SYZTransaction syzTransaction = method.getAnnotation(SYZTransaction.class);
        String groupId = "";
        //拿到注解之后我们就可以判断了
        if (syzTransaction.isStart() == true) {
            //那么我们就应该去创建事务组
            groupId = SYZTransactionalManager.createSYZTransactionGroup();
        } else {
            //添加事务，不管在最后还是中间事务，提交事务
        }
        //既然事务组 那么我们事务哪里来呢？ 这里指的事务并不是数据库事务，Er而是我们自己定义的事务 SYZTransactional
        SYZTransactional syzTransactional=SYZTransactionalManager.createSyzTransaction(groupId);
        //有了这个对象，那么我们就是把它加到事务组中去


        try {
            point.proceed();//这段代码就是走spring的逻辑，就是走完们自定义的切面就会走spring切面
            //而我们自己穿件事务的是否，需要传一个transactionType事务类型，而且是动态的，是传提交还是回滚呢，
            //也就是依据这里这个代码 point.proceed()是否报异常为准，为执行的时候没有异常则这是提交commit()操作
            //commit.....这里就是添加事务
            SYZTransactionalManager.addSYZTransactionGroup(syzTransactional,syzTransaction.isEnd(), TransactionType.commit);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //异常则是rollback();
            //这就解决了type问题
            SYZTransactionalManager.addSYZTransactionGroup(syzTransactional,syzTransaction.isEnd(), TransactionType.rollback);

        }


    }

    @Override
    public int getOrder() {
        return 1000000000;
    }
}
