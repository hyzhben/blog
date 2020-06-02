package com.blog.core.system.service;

import com.blog.core.constants.BaseEnums;
import com.blog.core.exception.ClaimTokenException;
import com.blog.core.system.dto.AuthToken;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RestTemplate restTemplate;

    //用户认证申请令牌，将令牌存储到redis
    public AuthToken login(String username, String password, String clientId, String clientSecret){
        //请求spring security申请令牌
        AuthToken authToken = this.applyToken(username,password,clientId,clientSecret);
        if(authToken == null){
            throw new ClaimTokenException(BaseEnums.FAIL_USERNMAE_OR_PASSWORD.desc());
        }
        //用户身份令牌
        String access_token = authToken.getAccess_token();
        //存储到redis中的内容
        JSONObject jsonObject = JSONObject.fromObject(authToken);
        String jsonString = jsonObject.toString();
        //将令牌存储到redis
        boolean result = this.saveToken(access_token,jsonString,tokenValiditySeconds);
        if(!result){
            throw new ClaimTokenException(BaseEnums.CLAIM_TOKEN_EXCEPTION.desc());
        }
        return authToken;
    }

    /**
     * 存储到令牌到redis
     ** @param access_token 用户身份令牌
     *      * @param content  内容就是AuthToken对象的内容
     *      * @param ttl 过期时间
     */
    private boolean saveToken(String access_token,String content,long ttl){
        String key = access_token;
        stringRedisTemplate.boundValueOps(key).set(content,ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key,TimeUnit.SECONDS);
        return expire>0;
    }

    //申请令牌
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret){
        //令牌申请的地址
        String authUrl = "http://localhost:8081/oauth/token";
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization",httpBasic);

        //定义body
        LinkedMultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);

        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(body,header);

        //设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST,httpEntity,Map.class);

        //申请令牌信息
        Map bodyMap = exchange.getBody();
        if(bodyMap == null || bodyMap.get("access_token") == null
                || bodyMap.get("jti") == null
                || bodyMap.get("refresh_token") == null){

            return null;
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token((String) bodyMap.get("access_token"));//用户身份令牌
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));//刷新令牌
        authToken.setJwt_token((String) bodyMap.get("jti"));//jwt令牌
        return authToken;
    }

    //获取httpBasic的串
    private String getHttpBasic(String clientId,String clientSecret){
        String string = clientId +":"+clientSecret;
        //将进行base64编码
        byte []  encode = Base64Utils.encode(string.getBytes());
        return "Basic "+new String(encode);
    }
}
