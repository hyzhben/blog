package com.blog.core.system.controller;

import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.BlogArticle;
import com.blog.core.system.service.IBlogService;
import com.blog.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BlogArticleController {

    @Autowired
    private IBlogService blogService;

   /* @RequestMapping(value="/service/blog/addArticle")
    @ResponseBody
    public Result addArticle(HttpServletRequest request){
        String content = request.getParameter("content");
        String state = request.getParameter("content");
        String files = request.getParameter("files");
        String title = request.getParameter("title");
        String type = request.getParameter("type");

    }*/
}
