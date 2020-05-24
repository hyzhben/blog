package com.blog.core.system.service;

import com.blog.core.system.dto.User;

import java.util.List;
import java.util.Map;

public interface ISysService {
    List<User> qryUserByList(Map<String,Object> param);

    User qryUserByUsername(String username);

}
