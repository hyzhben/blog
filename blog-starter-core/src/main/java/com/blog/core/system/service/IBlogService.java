package com.blog.core.system.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IBlogService {

    public void createBlogArticle(Map<String, Object> paramMap);

    public PageInfo qryArticleByPage(int page, int pageSize,Map<String,Object> paramMap);
}
