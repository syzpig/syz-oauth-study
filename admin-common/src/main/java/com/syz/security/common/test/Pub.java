package com.syz.security.common.test;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class Pub {
    /*  public static void main(String[] args) {
         JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1",6379,10000);
          //JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1",6379,3000);

          Jedis jedis = pool.getResource();
          Thread thread = new Thread(() -> {
              // jedis.psubscribe(new List(), "__keyevent@1__:expired","security_tbox_secret_key:*");
              //jedis.psubscribe(new List(), "__keyspace@1__:del");//修改队列
              //jedis.psubscribe(new List(), "__keyevent@1__:del");//修改队列
             // jedis.psubscribe(new List2(), "__keyevent@1__:expired");//过期队列
              jedis.psubscribe(new List(), "__keyevent@1__:*");//修改队列

          });
          thread.start();
      *//*    try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*//*
     *//*  Thread thread1 = new Thread(() -> {
            // jedis.psubscribe(new List(), "__keyevent@1__:expired","security_tbox_secret_key:*");
            //jedis.psubscribe(new List(), "__keyspace@1__:del");//修改队列
            //jedis.psubscribe(new List(), "__keyevent@1__:del");//修改队列
            jedis.psubscribe(new List2(), "__keyevent@1__:*");//过期队列
            //jedis.psubscribe(new List(), "__keyevent@1__:hset");//修改队列

        });
        thread1.start();*//*
    }*/
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379, 2000, "");
        // JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379, 3000);
        Jedis jedis = pool.getResource();
        jedis.select(1);
       // Map<String, String> stringStringMap = jedis.hgetAll("security_hu_secret_key:HU0000000000000046");
        // jedis.hset("secret_key_test:00001", "secret_key","2222");
        // Map<String, String> stringStringMap1 = jedis.hgetAll("secret_key_test:00001");
        //jedis.hset("security_hu_secret_key:HU0000000000000046", "security_hu_secret","45454");
        jedis.hset("secret_key_test:00002", "a", "11111");
        jedis.hset("secret_key_test:00002", "b", "22222");
        jedis.hset("secret_key_test:00002", "c", "33333");
        jedis.hset("secret_key_test:00002", "d", "44444");


       /* jedis.hset("secret_key_test:00002", "z", "4444");
        jedis.hset("secret_key_test:00002", "f", "4444");*/

        jedis.hset("security_hu_secret_key:00000000000000009", "test", "44");
        jedis.hset("security_hu_secret_key:00000000000000009", "1111", "44");

        //jedis.hset("security_hu_secret_key:00000000000000001", "key1", "444");
        //jedis.hset("security_hu_secret_key:00000000000000001", "111", "444");


      /*  jedis.hset("secret_key_test:00003", "aa", "111");
        jedis.hset("secret_key_test:00003", "bb", "11");
        jedis.hset("secret_key_test:00003", "cc", "1111");
        jedis.hset("secret_key_test:00003", "dd", "111");
        jedis.hset("secret_key_test:00003", "ee", "111");
        jedis.hset("secret_key_test:00003", "ff", "aaa11a");*/
      jedis.expire("secret_key_test:00002", 1);
       jedis.expire("security_hu_secret_key:00000000000000009", 2);
       // RedisTool.tryGetDistributedLock(jedis,"security_hu_secret_key:00000000000000001", UUID.randomUUID().toString(),4000);
        // jedis.hset("secret_key_test:00001", "secret_key1","11111");
      //  Map<String, String> stringStringMap2 = jedis.hgetAll("secret_key_test:00001");
        //jedis.del("secret_key_test:00001");
        Map<String, String> stringStringMap3 = jedis.hgetAll("secret_key_test:00001");
        System.out.println("test:" + stringStringMap3.get("secret_key_test:00001"));
    }

}
