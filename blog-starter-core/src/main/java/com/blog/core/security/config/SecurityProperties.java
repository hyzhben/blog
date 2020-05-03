package com.blog.core.security.config;

import org.springframework.stereotype.Component;

/**
 * Security 配置
 *
 */
@Component
//@ConfigurationProperties(prefix = SecurityProperties.PREFIX)
public class SecurityProperties {

    /**
     * 登录页面
     */
    private String loginPage = "/login";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

}
