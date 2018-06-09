package com.syz.security.common.util.jwt;

public interface IJWTInfo {
    /**
     *获取用户名
     */
    String getUniqueName();
    /**
     *获取用户Id
     */
    String getId();
    /**
     *获取名称
     */
    String getName();
}
