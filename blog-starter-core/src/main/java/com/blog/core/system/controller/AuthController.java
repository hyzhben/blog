package com.blog.core.system.controller;


import com.alibaba.druid.util.StringUtils;
import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.AuthToken;
import com.blog.core.system.service.AuthService;
import com.blog.core.system.common.util.CookieUtil;
import com.blog.core.system.common.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Value("${auth.clientId}")
    String clientId;

    @Value("${auth.clientSecret}")
    String clientSecret;

    @Value("${auth.cookieDomain}")
    String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
     public Result login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || StringUtils.isEmpty(username)){
            return  Results.failureWithData("姓名不能为空",BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
        }

        if(password == null || StringUtils.isEmpty(password)){
            return  Results.failureWithData("密码不能为空",BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
        }

        //申请令牌
        AuthToken authToken = authService.login(username,password,clientId,clientSecret);

        //用户身份令牌
        String access_token = authToken.getAccess_token();

        //将令牌存储到cookie
        this.saveCookie(access_token);

        return  Results.successWithData(access_token, BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
    }

    //将令牌存储到cookie
    private void saveCookie(String token){
        HttpServletResponse response =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    @PostMapping("/loginOut")
    public Result logout() {
        return null;
    }
}
