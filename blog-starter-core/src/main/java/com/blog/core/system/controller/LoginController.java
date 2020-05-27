package com.blog.core.system.controller;

import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.User;
import com.blog.core.system.service.ISysService;
import com.blog.core.util.Results;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private ISysService sysService;

    @RequestMapping(value="/login")
    @ResponseBody
    public Result login(HttpSession session, HttpServletRequest request) {
        String errorMsg = (String) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        String username = (String) session.getAttribute(User.FIELD_USERNAME);

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.removeAttribute(User.FIELD_USERNAME);

        if (StringUtils.isNotBlank(errorMsg)) {
            return Results.failureWithData(errorMsg, BaseEnums.FAIL_USERNMAE_OR_PASSWORD.code(), BaseEnums.FAIL_USERNMAE_OR_PASSWORD.desc());
        }
       if (StringUtils.isNotBlank(username)) {
                return   Results.failureWithData("login.username-or-password.error",BaseEnums.FAIL_USERNMAE_OR_PASSWORD.code(),BaseEnums.FAIL_USERNMAE_OR_PASSWORD.desc());
        }
            return Results.failureWithData(username, BaseEnums.FAIL_NOT_LOGIN.code(), BaseEnums.FAIL_NOT_LOGIN.desc());
    }

    @RequestMapping("/index")
    @ResponseBody
    public Result index() {
        return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
    }

    @RequestMapping("/login/invalid")
    @ResponseBody
    public Result loginInvalid(){
        return Results.failure(BaseEnums.SESSION_OVERDUE.code(),BaseEnums.SESSION_OVERDUE.desc());
    }

    @RequestMapping("/login/out")
    @ResponseBody
    public String loginOut(){
        return "out";
    }
}
