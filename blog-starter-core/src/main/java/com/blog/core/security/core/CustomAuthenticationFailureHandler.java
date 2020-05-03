package com.blog.core.security.core;

import com.blog.core.security.config.SecurityProperties;
import com.blog.core.security.utils.MessageAccessor;
import com.blog.core.system.dto.User;
import com.blog.core.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * 登录认证失败处理器
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserService userService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String username = httpServletRequest.getParameter("username");
        HttpSession session = httpServletRequest.getSession(false);
       /* if(session != null ){
            session.setAttribute("username",username);
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                    MessageAccessor.getMessage(e.getMessage(),e.getMessage()));
        }*/

       if(StringUtils.isNotBlank(username)){
           if(session ==null){
                session = httpServletRequest.getSession();
           }
           session.setAttribute("username",username);
           session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                   MessageAccessor.getMessage(e.getMessage(),e.getMessage()));
       }

        if(e instanceof BadCredentialsException){
            User user =userService.qryUserByUsername(username);
            //如果有要清空错误密码数的需要在这里清空输入错误密码次数
        }

        redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,securityProperties.getLoginPage());
    }
}
