package com.blog.core.system.controller;

import com.blog.core.base.BaseController;
import com.blog.core.config.FtpConfig;
import com.blog.core.util.FtpUtil;
import com.blog.core.util.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FtpConfig ftpConfig;

    @RequestMapping("/service/blog/uploadFiles")
    @ResponseBody
    public List<String> uploadFiles(MultipartFile[] files) throws IOException{
        List<String> picUrlList = new ArrayList<>();
            if(files.length > 0){
                logger.info("开始上传图片");
                for(MultipartFile file : files){
                    String oldName = file.getOriginalFilename();//获取图片原来的名字
                    String picNewName = UploadUtils.generateRandonFileName(oldName);//通过工具类参数新图片名称，防止重名
                    String picSavePath = UploadUtils.generateRandomDir(picNewName);//通过工具类把图片目录分级
                    String picUrl= FtpUtil.pictureUploadByConfig(ftpConfig,picNewName,picSavePath,file.getInputStream());//上传到图片服务器的操作
                    picUrlList.add(picUrl);
                }
            }
            return picUrlList;
    }

    @RequestMapping("/service/blog/test2")
    @ResponseBody
        public List<String> test2(MultipartFile[] files){
        List<String> picUrlList = new ArrayList<>();
        picUrlList=UploadUtils.uploadFiles(files);
        return picUrlList;
    }

}
