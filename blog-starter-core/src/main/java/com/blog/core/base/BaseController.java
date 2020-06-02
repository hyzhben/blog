package com.blog.core.base;

import com.blog.core.constants.Constants;
import com.blog.core.system.dto.User;
import net.sf.json.JSONObject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;
@SuppressWarnings(value = { "unchecked" })
public abstract class BaseController<T>  {

    protected Logger logger = null;
    private Class<T> clazzT;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public BaseController() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        clazzT = (Class<T>) type.getActualTypeArguments()[0];
        logger = LoggerFactory.getLogger(clazzT);
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public User getToken(){
        String tokenStr = WebUtils.toHttp(request).getHeader("Authorization");
        String token = tokenStr.split(" ")[1];
        Long expire = stringRedisTemplate.getExpire(token,TimeUnit.SECONDS);
        User user = null;

        if (expire > 0){
            String publickey = Constants.publickey;
            //校验jwt令牌
            Jwt jwt = JwtHelper.decodeAndVerify(token,new RsaVerifier(publickey));
            //拿到jwt令牌汇总自定义的内容
            String claims = jwt.getClaims();
            JSONObject json = JSONObject.fromObject(claims);
            user = new User();
            user.setUserId(Long.parseLong(json.getString("id")));
            user.setUsername(json.getString("user_name"));
            return user;
        }else{
            return user;
        }

    }

}
