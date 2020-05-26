package com.blog.core.system.service.impl;

import com.blog.core.config.IdGeneratorConfig;
import com.blog.core.system.dto.BlogArticle;
import com.blog.core.system.dto.BlogArticleFile;
import com.blog.core.system.extend.UploadFileDetails;
import com.blog.core.system.mapper.BlogArticleFileMapper;
import com.blog.core.system.mapper.BlogArticleMapper;
import com.blog.core.system.service.IBlogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private IdGeneratorConfig idGeneratorConfig;

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Autowired
    private BlogArticleFileMapper blogArticleFileMapper;

    @Override
    public void createBlogArticle(Map<String, Object> paramMap) {
        String content =  paramMap.get("content").toString();
        String status = paramMap.get("status").toString();
        List<UploadFileDetails> files = new ArrayList<>();
        JSONArray jsonArray  = JSONArray.fromObject(paramMap.get("files").toString());
        for(int i = 0 ; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            UploadFileDetails uploadFileDetails = (UploadFileDetails)JSONObject.toBean(jsonObject,UploadFileDetails.class);
            files.add(uploadFileDetails);
        }

        String userId = paramMap.get("userId").toString();
        Date currentTime = new Date();

        BlogArticle blogArticle = new BlogArticle();

        long articleId = idGeneratorConfig.getId();
        blogArticle.setArticleId(articleId);
        blogArticle.setContent(content);
        blogArticle.setStatus(Integer.parseInt(status));
        blogArticle.setCreateTime(currentTime);
        blogArticle.setCreateUser(Long.parseLong(userId));
        if(paramMap.containsKey("title") && StringUtils.isNotBlank( paramMap.get("title").toString())){
            blogArticle.setTitle(paramMap.get("title").toString());
        }
        if(paramMap.containsKey("typeId") && StringUtils.isNotBlank( paramMap.get("typeId").toString())){
            blogArticle.setTypeId(Long.parseLong(paramMap.get("typeId").toString()));
        }
        blogArticleMapper.insert(blogArticle);

        if(files.size() > 0){
            for(UploadFileDetails file : files){
                BlogArticleFile blogArticleFile = new BlogArticleFile();
                long fileId= idGeneratorConfig.getId();
                blogArticleFile.setFileId(fileId);
                blogArticleFile.setArticleId(articleId);
                blogArticleFile.setPicUrl(file.getFileUrl());
                blogArticleFile.setCreateTime(currentTime);
                blogArticleFile.setFileName(file.getFileName());
                blogArticleFile.setCreateUser(Long.parseLong(userId));
                blogArticleFileMapper.insert(blogArticleFile);
            }
        }
    }
}
