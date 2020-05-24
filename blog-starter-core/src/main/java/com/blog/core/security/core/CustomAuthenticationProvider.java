package com.blog.core.security.core;

import com.blog.core.exception.AccountNotExistsException;
import com.blog.core.exception.PasswordErrorException;
import com.blog.core.system.dto.User;
import com.blog.core.system.service.CustomUserDetailsService;
import com.blog.core.system.service.ISysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private ISysService sysService;

    @Autowired
    private CustomUserDetailsService detailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        User user = sysService.qryUserByUsername(username);
        if (user == null) {
            throw new AccountNotExistsException("user.error.login.username-or-password.error");
        }
        if (user.getEnabled()== 0 ) {
            throw new DisabledException("user.error.login.user.disabled");
        }
        return detailsService.loadUserByUsername(username);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        String username = userDetails.getUsername();
        User user = sysService.qryUserByUsername(username);


        // 检查密码是否正确
        String password = userDetails.getPassword();
        String rawPassword = usernamePasswordAuthenticationToken.getCredentials().toString();
        System.out.println("---------------------"+password+rawPassword+passwordEncoder.encode(password));
        boolean match = passwordEncoder.matches(rawPassword,password);
        if (!match) {
            throw new PasswordErrorException("user.error.login.username-or-password.error");
        }
    }
}
