package com.blog.core.system.mapper;

import com.blog.core.base.Mapper;
import com.blog.core.system.dto.BlogArticle;
import com.sun.javafx.collections.MappingChange;

import java.util.List;
import java.util.Map;

/**
 * BlogArticleDAO继承基类
 */
public interface BlogArticleMapper extends Mapper<BlogArticle> {

    public List<BlogArticle> qryArticleList(Map<String,Object> paramMap);

    public List<BlogArticle> qryNewestArticle();

}
