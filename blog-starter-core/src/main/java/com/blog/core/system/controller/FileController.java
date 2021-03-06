package com.blog.core.system.controller;

import com.blog.core.base.BaseController;
import com.blog.core.base.Result;
import com.blog.core.config.FtpConfig;
import com.blog.core.constants.BaseEnums;
import com.blog.core.system.extend.UploadFileDetails;
import com.blog.core.system.common.util.FtpUtil;
import com.blog.core.system.common.util.Results;
import com.blog.core.system.common.util.UploadUtils;
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
public class FileController extends BaseController<FileController> {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FtpConfig ftpConfig;

    @RequestMapping("/service/blog/uploadFiles")
    @ResponseBody
    public Result uploadFiles(MultipartFile[] files) throws IOException{
        List<UploadFileDetails> picInfoList = new ArrayList<UploadFileDetails>();
            if(files.length > 0){
                logger.info("开始上传图片");
                for(MultipartFile file : files){
                    UploadFileDetails uploadFileDetails = new UploadFileDetails();
                    String oldName = file.getOriginalFilename();//获取图片原来的名字
                    String picNewName = UploadUtils.generateRandonFileName(oldName);//通过工具类参数新图片名称，防止重名
                    String picSavePath = UploadUtils.generateRandomDir(picNewName);//通过工具类把图片目录分级
                    String picUrl= FtpUtil.pictureUploadByConfig(ftpConfig,picNewName,picSavePath,file.getInputStream());//上传到图片服务器的操作
                    uploadFileDetails.setFileName(oldName);
                    uploadFileDetails.setFileUrl(picUrl);
                    picInfoList.add(uploadFileDetails);
                }
            }
            return Results.successWithData(picInfoList, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }
}
