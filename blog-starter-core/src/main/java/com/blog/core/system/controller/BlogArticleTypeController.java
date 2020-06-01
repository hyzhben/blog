package com.blog.core.system.controller;

import com.blog.core.system.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BlogArticleTypeController {

    @Autowired
    private IBlogService blogService;


}
