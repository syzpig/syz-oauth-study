package com.syz.security.common.test;

import redis.clients.jedis.JedisPubSub;

public class List extends JedisPubSub {


    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe "
                + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(channel);
        System.out.println( channel.substring( channel.indexOf(":")+1,channel.length()));

        System.out.println("onPMessage pattern " + pattern + " " + channel + " " + message);
    }


}
