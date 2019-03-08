package com.syz.security.transaction.servicea.servicea.aspect;

import com.syz.security.transaction.servicea.servicea.connection.SYZConnection;
import com.syz.security.transaction.servicea.servicea.transactional.SYZTransactional;
import com.syz.security.transaction.servicea.servicea.transactional.SYZTransactionalManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 我们要获取一个connection,可以通过一个切面。
 */
@Aspect
@Component
public class SYZDataSourceAspect {

    /**
     * 我们可以直接切spring获取connection对象的方法，的dataSource接口里的方法
     * 我们就可以拿到spring的实现类返回的对象，
     * 所以这个切面什么也没干，直接切面到spring获取的connection连接
     */
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint point) throws Throwable {//参数指定是切点
        Connection connection = (Connection) point.proceed();//这里我们回去的就是spring本身返回的Connection连接
        //但是我们直接返回我们自己的链接也是可以的

        //在这里我们要拿到事务对象
        SYZTransactional syzTransactional= SYZTransactionalManager.getSyzTransaction();
        return new SYZConnection(connection,syzTransactional);//我们把拿到的connection给我们的自己定义的Connection,在我们自己的类中建个构造法，把这里获取的
        //connection传进去。拿到之后我们就开始可以执行事务操作比如提交或回滚等等。这些操作就是我们Connection中的饿commit和rollback方法。
        //我们如果自己不实现connection，就或执行spring实现的方法，我们既然实现了，就肯定执行我们自己Connection中的Commit方法等，
    }
}
