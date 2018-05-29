package com.syz.security.common.context;


import com.syz.security.common.constant.CommonConstants;
import com.syz.security.common.util.StringHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


/**
 * 全局当前操作信息相关类  任何地方，获取上下文操作，一般用ThreadLocal，获取当前线程上下文，一般是获取用户信息等，
 * 可直接访问RequestContext里面的写法，这里简易写法，通过map方式
 * 用于存储当前上下文信息等
 */
public class BaseContextHandler {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }
/*    public static void init() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
    }*/


    /**
     * 扩展一些方法
     */
    public static String getUserID() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_ID);
        return StringHelper.getObjectValue(value);
    }

    public static String getUsername() {
        Object value = get(CommonConstants.CONTEXT_KEY_USERNAME);
        return StringHelper.getObjectValue(value);
    }

    public static String getName() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_NAME);
        return StringHelper.getObjectValue(value);
    }

    public static void setUserID(String userID) {
        set(CommonConstants.CONTEXT_KEY_USER_ID, userID);
    }

    public static void setUsername(String username) {
        set(CommonConstants.CONTEXT_KEY_USERNAME, username);
    }

    public static void setName(String name) {
        set(CommonConstants.CONTEXT_KEY_USER_NAME, name);
    }

    public static void remove() {
        threadLocal.remove();
    }

    //下面是单元测试，，工具类可以这么写，
    @RunWith(MockitoJUnitRunner.class)
    public static class UnitTest {
        private Logger logger = LoggerFactory.getLogger(UnitTest.class);

        @Test
        public void testSetContextVariable() throws InterruptedException {
            BaseContextHandler.set("test", "main");
            //模拟多线程
            new Thread(() -> {
                BaseContextHandler.set("test", "moo");

                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                assertEquals(BaseContextHandler.get("test"), "moo");
                logger.info("thread one done!");
            }).start();
            new Thread(() -> {
                BaseContextHandler.set("test", "moo2");
                assertEquals(BaseContextHandler.get("test"), "moo2");
                logger.info("thread two done!");
            }).start();

            Thread.currentThread().sleep(5000);
            assertEquals(BaseContextHandler.get("test"), "main");
            logger.info("main one done!");
        }

        @Test
        public void testSetUserInfo() {
            BaseContextHandler.setUserID("test");
            assertEquals(BaseContextHandler.getUserID(), "test");
            BaseContextHandler.setUsername("test2");
            assertEquals(BaseContextHandler.getUsername(), "test2");
        }
    }
}
