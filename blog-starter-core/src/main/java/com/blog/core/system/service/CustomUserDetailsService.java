package com.blog.core.system.service;

import com.blog.core.exception.AccountNotExistsException;
import com.blog.core.system.dto.User;
import com.blog.core.system.extend.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ISysService sysService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user= sysService.qryUserByUsername(s) ;
        if(user == null){
            throw new AccountNotExistsException("user.error.login.username-or-password.error");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getUserId(),
                user.getNickname(), authorities);
    }
}
