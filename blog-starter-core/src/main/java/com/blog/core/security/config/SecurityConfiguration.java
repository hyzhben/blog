package com.blog.core.security.config;

import com.blog.core.security.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        //开启跨域cors()
//                .cors().and().csrf().disable()
//                .authorizeRequests().requestMatchers(CorsUtilt::isCorsRequest)
//                .permitAll()
//                .and()
                .authorizeRequests()
                .antMatchers("/static/**","/webjars/**","/public/**","/login","favicon.ico")
                .permitAll()//允许匿名访问的地址
                .and()//使用and()方法相当于XML标签的关闭，这样允许我们继续配置父类节点
            .authorizeRequests()
                .anyRequest()
                .authenticated()//其他地址都需要进行认证
                .and()
            .formLogin()//启动表单登录
                .loginPage("/login")//登录页面
                .defaultSuccessUrl("/index")//默认的登录成功后的跳转地址
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
            .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler)//退出成功处理器，不能喝logoutSuccessUrl同时使用
                .logoutSuccessUrl("/")//退出成功跳到首页
                .deleteCookies("JSESSIONID", "SESSION") // 删除 Cookie
                .and()
            .sessionManagement()
                .invalidSessionUrl("/login/invalid")//session失效后跳转的页面
                .maximumSessions(1)//设置同一用户最多存在几个Session,后登陆的用户将踢掉前面的用户
                .expiredSessionStrategy(new CustomSessionInformationExpiredStrategy())//用户失效处理
                .and()
                .and()
            .csrf()
            .disable();
    }

    /**
     * 设置认证处理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
        super.configure(auth);
    }

    /**
     * 密码处理器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
