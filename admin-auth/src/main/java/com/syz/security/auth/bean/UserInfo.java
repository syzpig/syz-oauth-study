package com.syz.security.auth.bean;

import com.syz.security.common.util.jwt.IJWTInfo;

import java.util.Objects;

public class UserInfo implements IJWTInfo {
    String username;
    String name;
    String userId;
    String password;
    String phone;
    int age;

    public UserInfo(String username, String name, String userId, String password) {
        this.username = username;
        this.name = name;
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(username, userInfo.username) &&
                Objects.equals(userId, userInfo.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, userId);
    }
}
