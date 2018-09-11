package com.syz.security.common.test;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisLock {

    /**
     * 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒
     */
    private static final long MILLI_NANO_CONVERSION = 1000 * 1000L;

    private static final String EXPIRE_KEY_PREFIX = "lock:%s";

    private boolean locked;

    private String key;

    public RedisLock(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Param key can not be null or empty");
        }
        this.key = String.format(EXPIRE_KEY_PREFIX, key);
        this.locked = false;
    }

    /**
     * 获取锁
     * 在timeout时间内会一直尝试获取锁，如果timeout=0，则表示获取失败后直接返回不再尝试
     * 锁的有效期expireSecs必须大于0，当超过有效期未被unlock时，系统将会强制释放
     *
     * @param timeout    获取锁的等待时间
     * @param expireSecs 锁的有效时间，必须大于0
     * @return
     */
    public boolean lock(long timeout, int expireSecs) {
        if (expireSecs <= 0) {
            throw new IllegalArgumentException("Param expireSecs must lager than zero.");
        }
        long nano = System.nanoTime();
        timeout *= MILLI_NANO_CONVERSION;
        String lockStart = String.valueOf(System.currentTimeMillis());
         JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379, 3000);
        Jedis jedis = pool.getResource();
        jedis.select(1);

        try {
            while ((System.nanoTime() - nano) < timeout) {
                if (jedis.setnx(this.key, lockStart) == 1) {
                    jedis.expire(this.key, expireSecs);
                    this.locked = true;
                    return this.locked;
                }
                Thread.sleep(3, RandomUtils.nextInt(500));
            }

            String expireStr = jedis.get(key);
            Long now = System.currentTimeMillis();
            String nowStr = String.valueOf(now);
            if (!StringUtils.isNumeric(expireStr)) {
                return false;
            }
            //ttl小于0 表示key上没有设置生存时间（key是不会不存在的，因为前面setnx会自动创建）
            //出现这种状况，是因为某个实例setnx成功后，expire由于各种可能原因而没有被调用造成的
            //这时可以直接设置expire来占有锁
            long ttlValue = jedis.ttl(this.key);
            if (ttlValue < 0) {
                jedis.setnx(key, nowStr);
                jedis.expire(key, expireSecs);
                return true;
            }

            Long expireLong = Long.parseLong(expireStr);
            if (now - expireLong > expireSecs * 1000) {
                jedis.del(key);
                jedis.setnx(key, nowStr);
                jedis.expire(key, expireSecs);
                return true;
            }

        } catch (Exception e) {
            if (jedis != null) {
                //JedisUtil.returnBrokenResource(jedis);
            }
            throw new RuntimeException("Locking error", e);
        } finally {
            if (jedis != null) {
                // JedisUtil.returnResource(jedis);
            }
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @return
     */
    public boolean unLock() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379, 3000);
        Jedis jedis = pool.getResource();
        jedis.select(1);


        try {
            if (jedis.del(this.key) == 1) {
                this.locked = false;
                return true;
            }
            return false;
        } catch (Exception e) {
            if (jedis != null) {
                // JedisUtil.returnBrokenResource(jedis);
            }
            throw new RuntimeException("UnLocking error", e);
        } finally {
            if (jedis != null) {
                //JedisUtil.returnResource(jedis);
            }
        }
    }


}
