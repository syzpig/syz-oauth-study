package com.syz.security.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.syz.security.common.constant.CommonConstants;
import com.syz.security.common.msg.BaseResponse;
import com.syz.security.common.msg.ObjectRestResponse;
import com.syz.security.common.util.jwt.IJWTInfo;
import com.syz.security.zuul.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 构建授权服务器后在网关构建网关拦截器，验证JWTtoken的有效验证，这里是在网关统一验证，也可以在后台各服务中去验证
 */
@Component
public class AccessFilter extends ZuulFilter {

    @Value("${zuul.prefix}")
    private String prefix;
    @Value("${jwt.ignore-url.jwtIgnoreUrl}")
    private String jwtIgnoreUrl;
    @Value("${jwt.token-header.jwtTokenHeader}")
    private String jwtTokenHeader;

  /*  @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${client.token-header}")
    private String clientTokenHeader;*/
 /*   @Autowired
    private ClientAuthRpc clientAuthRpc;*/

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *验证
     */
    @Override
    public Object run() {
        //获取网关的上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();  //获取当前请求
        if (isIgnorePath(request.getRequestURI()))  //忽略地址进行验证
            return null;
        String token = request.getHeader(jwtTokenHeader);//请求获取头部信息
        try {
            // 验证用户合法性  验证token合法性
            IJWTInfo infoFromToken = jwtUtil.getInfoFromToken(token);
            // todo 用户权限
            // 申请客户端密钥头
           /* BaseResponse resp = clientAuthRpc.getAccessToken(clientId, clientSecret);
            if(resp.getStatus() == 200){
                ObjectRestResponse<String> clientToken = (ObjectRestResponse<String>) resp;
                ctx.addZuulRequestHeader(clientTokenHeader,clientToken.getData());
            }else {
                throw new ClientInvalidException("Gate client secret is Error");
            }*/
        }/* catch (ClientInvalidException ex){
            ctx.setResponseBody(JSON.toJSONString(new BaseResponse(ex.getStatus(), ex.getMessage())));

        }*/ catch (Exception e) {
            ctx.setResponseBody(JSON.toJSONString(new BaseResponse(CommonConstants.EX_TOKEN_ERROR_CODE, "Token error or Token is Expired！")));
            e.printStackTrace();
        }
        return null;
    }

    private boolean isIgnorePath(String path) {
        for (String url : jwtIgnoreUrl.split(",")) {
            if (path.substring(prefix.length()).startsWith(url)) { //startsWith以什么开头
                return true;
            }
        }
        return false;
    }
}
