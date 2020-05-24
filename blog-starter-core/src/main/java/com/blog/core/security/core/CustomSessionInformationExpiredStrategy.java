package com.blog.core.security.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 并发登陆导致Session失效的处理策略类
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    private static  final Logger LOGGER = LoggerFactory.getLogger(CustomSessionInformationExpiredStrategy.class);

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        HttpServletRequest request = sessionInformationExpiredEvent.getRequest();
            HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        LOGGER.warn("==> 并发登陆：当前账号在[{}]上登陆", request.getRequestURI());
    }
}
