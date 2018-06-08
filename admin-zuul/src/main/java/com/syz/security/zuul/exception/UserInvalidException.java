package com.syz.security.zuul.exception;

import com.syz.security.common.constant.CommonConstants;
import com.syz.security.common.exception.BaseException;

/**
 *创建用户校验自定义异常
 */
public class UserInvalidException extends BaseException{
    private static int STATUS_COOD=400111;//创建自定义异常返回码
    public UserInvalidException(String message){
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
