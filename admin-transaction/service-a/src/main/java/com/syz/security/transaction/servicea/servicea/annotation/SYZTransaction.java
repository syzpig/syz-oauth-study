package com.syz.security.transaction.servicea.servicea.annotation;
/**
 *3.接下来我们来定义一个注解，来实现事务控制。
 */
public @interface SYZTransaction {
    //我们事务组，在发起方创建就可以了，后面就不用创建了，直接加进去就好了，那么怎么区分哪个事务组的开始呢？
    //此注解就是来区分的，定义两个属性

    boolean isStart() default false;//代表是分布式事务的开始
    boolean isEnd() default false;//代表是分布式事务的结束

}
