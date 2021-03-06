package com.blog.core.system.controller;

import com.blog.core.base.BaseController;
import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.common.enums.BlogArticleStatusEnums;
import com.blog.core.system.dto.BlogArticle;
import com.blog.core.system.dto.User;
import com.blog.core.system.extend.MBlogArticle;
import com.blog.core.system.service.IBlogService;
import com.blog.core.system.service.ISysService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import com.blog.core.system.common.util.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BlogArticleController extends BaseController<BlogArticleController> {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ISysService sysService;

    /**
     * 文章新增
     * @return
     */
    @RequestMapping(value="/service/blog/createArticle")
    @ResponseBody
    public Result createArticle(){
        try{
            String content = request.getParameter("content");
            String status = request.getParameter("status");
            String files = request.getParameter("files");
            String title = request.getParameter("title");
            String typeId = request.getParameter("typeId");

            User user = getToken();

            if(user == null){
                return Results.failure(BaseEnums.TOKEN_OVERDUE.code(), BaseEnums.TOKEN_OVERDUE.desc());
            }

            Map<String,Object> paramMap = new HashMap<String,Object>();
            if(content == null || StringUtils.isBlank(content)){
                return Results.failureWithData("内容不能为空", BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
            }

            if(status == null || StringUtils.isBlank(status)){
                return Results.failureWithData("发布状态不能为空", BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
            }

            if(files != null && StringUtils.isNotBlank(files)){
                paramMap.put("files",files);
            }

            if(title != null && StringUtils.isNotBlank(title)){
                paramMap.put("title",title);
            }

            if(typeId != null && StringUtils.isNotBlank(typeId)){
                paramMap.put("typeId",typeId);
            }

            paramMap.put("status",status);
            paramMap.put("content",content);
            paramMap.put("userId",user.getUserId());

            blogService.createBlogArticle(paramMap);
            return Results.successWithData("文章上传成功",BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
        }catch (Exception e){
            logger.error(e.getMessage());
            return Results.failure(BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
        }
    }

    /**
     * 文章列表查询
     */
    @RequestMapping(value="/service/blog/qryBlogArticle")
    @ResponseBody
    public Result qryBlogArticle(){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        try{
            if(currentPage == null || StringUtils.isBlank(currentPage)){
                return Results.failureWithData("当前页不能为空",BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
            }
            if(pageSize == null || StringUtils.isBlank(pageSize)){
                return Results.failureWithData("一页大小不能为空",BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("status", BlogArticleStatusEnums.PUBLISH.getValue());
            PageInfo result = blogService.qryArticleByPage(Integer.parseInt(currentPage),Integer.parseInt(pageSize),paramMap);

            List<MBlogArticle> mBlogArticleList = new ArrayList<MBlogArticle>();
            if(result.getList().size()>0){
                BlogArticle blogArticle = null;
                List<Long> idsList = new ArrayList<>();
                for(Object object : result.getList()){
                    blogArticle = (BlogArticle)object;
                    idsList.add(blogArticle.getCreateUser());
                }
                List<User> userList = sysService.qryUserByIds(idsList);
                Map<Long,Object> userMap = new HashMap<>();
                for(User user : userList){
                    userMap.put(user.getUserId(),user.getPicUrl());
                }

                //翻译
                MBlogArticle mBlogArticle = null;
                for(Object object : result.getList()){
                    blogArticle = (BlogArticle)object;
                    mBlogArticle = new MBlogArticle();
                    BeanUtils.copyProperties(blogArticle,mBlogArticle);
                    mBlogArticle.setPicUrl(userMap.get(mBlogArticle.getCreateUser()).toString());
                    mBlogArticleList.add(mBlogArticle);
                }

            }
            result.setList(mBlogArticleList);
             return Results.successWithData(result,BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
          }catch (Exception e){
            logger.error(e.getMessage());
            return Results.failure(BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
        }
    }

    /**
     * 最新发布文章
     * @return
     */
    @RequestMapping("/service/blog/qryNewestArticle")
    @ResponseBody
    public Result qryNewestArticle(){
        try{
            List<BlogArticle> blogArticleList = blogService.qryNewestArticle();
            return Results.successWithData(blogArticleList,BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
        }catch (Exception e){
            logger.error(e.getMessage());
            return Results.failure(BaseEnums.FAILURE.code(), BaseEnums.FAILURE.desc());
        }
    }
}
