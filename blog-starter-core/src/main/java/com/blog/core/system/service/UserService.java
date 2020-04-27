package com.blog.core.system.service;

import com.blog.core.base.Service;
import com.blog.core.system.dto.User;

import java.util.List;
import java.util.Map;

/**
 * 用户service接口
 */
public interface UserService extends Service<User> {

    List<User> qryUserByList(Map<String,Object> param);
}
