package com.syz.security.common.exception.auth;


import com.syz.security.common.constants.CommonConstants;
import com.syz.security.common.exception.BaseException;

/**
 * Created by ace on 2017/9/10.
 */
public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
