package com.syz.security.common.exception.auth;

import com.syz.security.common.constant.RestCodeConstants;
import com.syz.security.common.msg.BaseResponse;
/**
 *
 * 创建token异常
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
