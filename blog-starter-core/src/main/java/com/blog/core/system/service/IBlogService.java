package com.blog.core.system.service;

import com.blog.core.system.dto.BlogArticle;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IBlogService {

    public void createBlogArticle(Map<String, Object> paramMap);

    public PageInfo qryArticleByPage(int page, int pageSize,Map<String,Object> paramMap);

    public List<BlogArticle> qryNewestArticle();
}
