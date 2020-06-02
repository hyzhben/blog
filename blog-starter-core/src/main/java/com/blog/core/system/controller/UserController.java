package com.blog.core.system.controller;

import com.blog.core.base.BaseController;
import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.User;
import com.blog.core.system.service.ISysService;
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
public class UserController extends BaseController<UserController> {

    @Autowired
    private ISysService sysService;

    /**
     * Logger
     * @return
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation("查找所有用户")
    @ApiImplicitParam(name="paramMap",value="user",paramType="map")
    @PostMapping("/service/sys/qryUserByList")
    public Result qryUserByList(){
        Map<String,Object> paramMap = new HashMap<>();
        List<User> list = sysService.qryUserByList(paramMap);
        return Results.successWithData(list, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

}
