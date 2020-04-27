package com.blog.core.system.controller;

import com.blog.core.base.BaseController;
import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.User;
import com.blog.core.system.service.UserService;
import com.blog.core.util.Results;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * Logger
     * @return
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/sys/user/queryAll")
    public Result queryAll(){
        List<User> list = userService.selectAll();
        logger.debug("userId:{},username:{},birthday:{}",list.get(0).getUserId(),list.get(0).getUsername(),list.get(0).getBirthday());
        return Results.successWithData(list, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

    @ApiOperation("查找所有用户")
    @ApiImplicitParam(name="paramMap",value="user",paramType="map")
    @PostMapping("/sys/user/qryUserByList")
    public Result qryUserByList(){
        Map<String,Object> paramMap = new HashMap<>();
        List<User> list = userService.qryUserByList(paramMap);
        return Results.successWithData(list, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

}
