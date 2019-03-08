package com.syz.security.transaction.servicea.servicea.utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 *AQS的功能可以分为独占和共享，ReentrantLock实现了独占功能。ReentrantLock实现了Lock接口，加锁和解锁都需要显式写出，注意一定要在适当时候unlock
 *
 * 和synchronized相比，ReentrantLock用起来会复杂一些。在基本的加锁和解锁上，两者是一样的，所以无特殊情况下，
 * 推荐使用synchronized。ReentrantLock的优势在于它更灵活、更强大，增加了轮训、超时、中断等高级功能。
 *
 * ReentrantLock的内部类Sync继承了AQS，分为公平锁FairSync和非公平锁NonfairSync
 *      公平锁：线程获取锁的顺序和调用lock的顺序一样，FIFO；
        非公平锁：线程获取锁的顺序和调用lock的顺序无关，全凭运气
 *ReentrantLock默认使用非公平锁是基于性能考虑，公平锁为了保证线程规规矩矩地排队，需要增加阻塞和唤醒的时间开销。如果直接插队获取非公平锁，跳过了对队列的处理，速度会更快。
 * https://www.jianshu.com/p/5d97713f7cef
 */
public class Task {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     *阻塞
     */
    public void waitTask() {
        try {
            lock.lock();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     *唤醒
     */
    public void signalTask() {
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
