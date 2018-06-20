package com.syz.security.common.redisLock;

public class test {
    public static void main(String[] args) {
        JedisUtils jedisUtils = new JedisUtils();
        ThreadA threadA = new ThreadA(jedisUtils);
        threadA.start();
    }
}
