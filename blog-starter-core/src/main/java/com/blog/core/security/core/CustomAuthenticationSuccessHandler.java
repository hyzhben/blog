package com.blog.core.security.core;

import com.blog.core.system.dto.User;
import com.blog.core.system.service.ISysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录认证成功处理器
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private ISysService sysService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = request.getParameter("username");
        User user = sysService.qryUserByUsername(username);

        HttpSession session = request.getSession(false);
       if(session != null ){
            session.setAttribute("user",user);
        }

        //登录成功清除错误密码数
        //        userService.loginSuccess(user.getUserId());

        getRedirectStrategy().sendRedirect(request,response,"/index");
    }
}
