package com.syz.security.common.test;

import redis.clients.jedis.JedisPubSub;

public class List2 extends JedisPubSub {


    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe "
                + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        String str = message;
        message.substring( str.indexOf(":"),str.length()-1);

        System.out.println("onPMessage pattern " + pattern + "======= " + channel + " ======" + message);
    }


}
