package com.blog.core.system.controller;

import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.dto.User;
import com.blog.core.system.service.IBlogService;
import com.blog.core.util.Results;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BlogArticleController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IBlogService blogService;

    @RequestMapping(value="/service/blog/createArticle")
    @ResponseBody
    public Result createArticle(HttpServletRequest request){
        try{
            String content = request.getParameter("content");
            String status = request.getParameter("status");
            String files = request.getParameter("files");
            String title = request.getParameter("title");
            String typeId = request.getParameter("typeId");
            User user= (User)request.getSession().getAttribute("user");

            Map<String,Object> paramMap = new HashMap<String,Object>();
            if(content == null || StringUtils.isBlank(content)){
                return Results.failureWithData("内容不能为空",BaseEnums.FAILURE.code(),BaseEnums.FAILURE.desc());
            }

            if(status == null || StringUtils.isBlank(status)){
                return Results.failureWithData("发布状态不能为空",BaseEnums.FAILURE.code(),BaseEnums.FAILURE.desc());
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

            return Results.successWithData(user,BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
        }catch (Exception e){
            logger.error(e.getMessage());
            return Results.failure(BaseEnums.FAILURE.code(),BaseEnums.FAILURE.desc());
        }
    }
}
