package com.blog.core.system.controller;

import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.BlogArticle;
import com.blog.core.system.service.BlogArticleService;
import com.blog.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BlogArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    @RequestMapping(value="/service/blog/qryBlogArticleList")
    @ResponseBody
    public Result qryBlogArticleList(){
        List<BlogArticle>  blogArticleList = blogArticleService.selectAll();
        return  Results.successWithData(blogArticleList, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

}
