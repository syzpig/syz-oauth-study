package com.syz.security.transaction.servicea.servicea.aspect;

import com.syz.security.transaction.servicea.servicea.annotation.SYZTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 4.还需要与事务注解对应的一个切面
 */

@Aspect
@Component
public class SYZTransactionAspect {
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
        //拿到注解之后我们就可以判断了
        if (syzTransaction.isStart() == true) {
            //那么我们就应该去创建事务组
        } else {
            //添加事务，不管在最后还是中间事务，提交事务
        }
        //既然事务组 那么我们事务哪里来呢？ 这里指的事务并不是数据库事务，Er而是我们自己定义的事务 SYZTransactional
    }
}
