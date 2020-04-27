package com.blog.core.system.service.impl;

import com.blog.core.base.BaseService;
import com.blog.core.system.dto.User;
import com.blog.core.system.mapper.UserMapper;
import com.blog.core.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户Service实现类
 */
@Service
public class UserServiceImpl extends BaseService<User>  implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    @Cacheable(cacheNames = "my-redis-cache1")
    public List<User> qryUserByList(Map<String, Object> param) {
        return userMapper.qryUserByList(param);
    }
}
