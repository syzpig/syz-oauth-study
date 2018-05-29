package com.syz.security.common.exception.auth;


import com.syz.security.common.constant.RestCodeConstants;
import com.syz.security.common.msg.BaseResponse;

/**
 *
 */
public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
