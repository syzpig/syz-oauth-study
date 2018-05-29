package com.syz.security.common.exception;

/**
 * Created by ace on 2017/9/8.
 * 创建异常处理工具类  继成RuntimeException是不需要声明式抛出  例如admin-user服务测试异常用例，集成Exception则需要声明式抛出
 */
public class BaseException extends RuntimeException {
    private int status = 200;  //创建异常编码

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseException() {
    }

    public BaseException(String message, int status) {
        super(message);
        this.status = status;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
