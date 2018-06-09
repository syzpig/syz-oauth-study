package com.syz.security.auth.exception;

import com.syz.security.common.constant.CommonConstants;
import com.syz.security.common.exception.BaseException;

/**
 *创建用户校验自定义异常
 */
public class ClientInvalidException extends BaseException{

    public ClientInvalidException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
