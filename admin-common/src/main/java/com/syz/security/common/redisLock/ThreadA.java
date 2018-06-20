package com.syz.security.common.redisLock;

public class ThreadA extends Thread{
    private JedisUtils jedisUtils;

    public ThreadA(JedisUtils jedisUtils) {
        this.jedisUtils = jedisUtils;
    }

    @Override
    public void run() {
        jedisUtils.seckill();
    }
}
