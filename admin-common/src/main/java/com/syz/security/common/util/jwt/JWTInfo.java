package com.syz.security.common.util.jwt;

import java.io.Serializable;
import java.util.Objects;

public class JWTInfo implements Serializable, IJWTInfo {
    private String userId;
    private String userName;
    private String name;

    public JWTInfo(String userName, String userId, String name) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
    }

    public String getId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUniqueName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JWTInfo jwtInfo = (JWTInfo) o;
        return Objects.equals(userId, jwtInfo.userId) &&
                Objects.equals(userName, jwtInfo.userName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userName);
    }
}
