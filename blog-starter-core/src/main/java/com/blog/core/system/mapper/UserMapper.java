package com.blog.core.system.mapper;

import com.blog.core.base.Mapper;
import com.blog.core.system.dto.User;

import java.util.List;
import java.util.Map;

/**
 * @name UserMapper
 */
public interface UserMapper extends Mapper<User> {
    /**
     * 自定义查询条件
     */
    List<User> qryUserByList(Map<String, Object> param);

    User qryUserByUsername(String username);
}
