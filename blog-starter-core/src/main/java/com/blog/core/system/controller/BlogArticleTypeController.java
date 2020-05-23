package com.blog.core.system.controller;

import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.BlogArticleType;
import com.blog.core.system.service.BlogArticleTypeService;
import com.blog.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogArticleTypeController {

    @Autowired
    private BlogArticleTypeService blogArticleTypeService;

    @RequestMapping(value="/service/blog/getArticleTypeById")
    @ResponseBody
    public Result getArticleTypeById(){
        BlogArticleType blogArticleType = blogArticleTypeService.get(1L);
        return Results.successWithData(blogArticleType, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }
}
