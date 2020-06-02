package com.blog.core.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.blog.core.base.BaseEnum;
import com.blog.core.constants.BaseEnums;
import com.blog.core.exception.PasswordErrorException;
import com.blog.core.system.dto.UserExt;
import com.blog.core.system.dto.UserJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    SysServiceImpl sysService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //取出身份,如果身份为空说明没有认证
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //没有认证统一采用httpbasic认证,httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
            if(authentication == null){
                ClientDetails clientDetails = clientDetailsService.loadClientByClientId(s);
                if(clientDetails != null){
                    //密码
                    String clientSecret = clientDetails.getClientSecret();
                    return new User(s,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                }
            }
            if(StringUtils.isEmpty(s)){
                return null;
        }

        com.blog.core.system.dto.User user = sysService.qryUserByUsername(s);

        if(user == null){
            throw new PasswordErrorException(BaseEnums.FAIL_USERNMAE_OR_PASSWORD.code(), BaseEnums.FAIL_USERNMAE_OR_PASSWORD.desc());
        }
        String password = user.getPassword();
//        //用户权限，这里暂时使用静态数据，最终会从数据库读取
//        //从数据库获取权限
//        List<XcMenu> permissions = userext.getPermissions();
//        List<String> user_permission = new ArrayList<>();
//        permissions.forEach(item-> user_permission.add(item.getCode()));
////        user_permission.add("course_get_baseinfo");
////        user_permission.add("course_find_pic");
//        String user_permission_string  = StringUtils.join(user_permission.toArray(), ",");

        UserJwt userDetails = new UserJwt(s,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        userDetails.setId(user.getUserId());
        userDetails.setName(user.getUsername());//用户名称
        return userDetails;
    }
}
