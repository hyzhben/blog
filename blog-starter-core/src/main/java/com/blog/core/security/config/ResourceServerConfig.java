
package com.blog.core.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


/**
 * 这个类表明了此应用是OAuth2 的资源服务器,此处主要指定受资源服务器保护的资源链接
 * 默认情况下spring security oauth2的http配置会被WebSecurityConfigurerAdapter的配置覆盖
 *
 * @author liuzj
 * @date 2019-01-15
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {


/*http.csrf().disable()//禁用了csrf（跨站请求伪造）功能
                .authorizeRequests()//限定签名成功的请求
                //必须认证过后才可以访问;注意：hasAnyRole 会默认加上ROLE_前缀，而hasAuthority不会加前缀
                .antMatchers("/decision/**","/govern/**").hasAnyRole("user") // 在角色过滤的时候需要注意user角色需要加角色前缀
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/test/**").authenticated()
                // 免验证请求
                .antMatchers("/oauth/**").permitAll();*/

        http.csrf().disable()//禁用了csrf（跨站请求伪造）功能
            .authorizeRequests()
            .antMatchers("/oauth/**","/loginOut","login","/**").permitAll()//放行的路径
            .anyRequest()
            .authenticated();
    }

}

