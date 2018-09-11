package com.syz.security.common.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class test {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
        Jedis jedis = pool.getResource();
     /*   jedis.set("security_tbox_secret_key:TBOX00000000000003", "你还在吗");
        jedis.expire("security_tbox_secret_key:TBOX00000000000003", 1);
        jedis.hset("secret_key_test:00001", "secret_key","332333");*/
        // jedis.expire("secret_key_test:00001", 1);
        jedis.select(1);
        jedis.hset("secret_key_test:00001", "secret_key", "111111");
        Map<String, String> stringStringMap2 = jedis.hgetAll("secret_key_test:00001");
        //jedis.del("secret_key_test:00001");
     /*   try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        jedis.expire("secret_key_test:00001", 10);
        Map<String, String> stringStringMap3 = jedis.hgetAll("secret_key_test:00001");
        System.out.println(stringStringMap3.get("secret_key_test:00001"));
    }


}
