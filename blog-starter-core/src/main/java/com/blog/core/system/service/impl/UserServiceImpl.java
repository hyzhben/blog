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


    /**
     * @Cacheable：在方法执行前Spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；没有则调用方法并将方法返回值放进缓存。
     *
     * @CachePut：将方法的返回值放到缓存中。
     *
     * @CacheEvict：删除缓存中的数据。
     * @param param
     * @return
     */
    @Override
    @Cacheable(value = "globalUser")
    public List<User> qryUserByList(Map<String, Object> param) {
        return userMapper.qryUserByList(param);
    }

    @Override
    public User qryUserByUsername(String username) {
        return userMapper.qryUserByUsername(username);
    }
}
