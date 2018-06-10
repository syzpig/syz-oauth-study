package com.syz.security.common.exception.auth;

import com.syz.security.common.constant.CommonConstants;
import com.syz.security.common.exception.BaseException;

public class ClientForbbionException extends BaseException{
    public ClientForbbionException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }
}
