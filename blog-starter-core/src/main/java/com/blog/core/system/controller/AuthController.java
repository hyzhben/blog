package com.blog.core.system.controller;


import com.alibaba.druid.util.StringUtils;
import com.blog.core.base.Result;
import com.blog.core.system.dto.AuthToken;
import com.blog.core.system.service.AuthService;
import com.blog.core.util.CookieUtil;
import com.blog.core.util.Results;
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

    @PostMapping("/userlogin")
     public Result login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || StringUtils.isEmpty(username)){
            return  Results.failureWithData("请输入姓名","1001","错误");
        }

        if(password == null || StringUtils.isEmpty(password)){
            return  Results.failureWithData("请输入密码","1001","错误");
        }

        //申请令牌
        AuthToken authToken = authService.login(username,password,clientId,clientSecret);

        //用户身份令牌
        String access_token = authToken.getAccess_token();

        //将令牌存储到cookie
        this.saveCookie(access_token);

        return  Results.successWithData(access_token,"1000","登录成功");
    }

    //将令牌存储到cookie
    private void saveCookie(String token){
        HttpServletResponse response =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    public Result logout() {
        return null;
    }
}
