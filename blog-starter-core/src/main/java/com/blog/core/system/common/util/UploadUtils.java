package com.blog.core.system.common.util;

import com.blog.core.config.ApplicationContextConfig;
import com.blog.core.config.FtpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadUtils {

    private static Logger logger = LoggerFactory.getLogger(UploadUtils.class);

    /**
     * 获取真实文件名
     * @param fileName
     * @return
     */
    public static String subFileName(String fileName){
        //查找最后一个\(文件分隔符)位置
        int index  = fileName.lastIndexOf(File.separator);
        if(index == -1){
            //没有分隔符，说明是真实名称
            return fileName;
        }else{
            return fileName.substring(index+1);
        }
    }

    /**
     * 获取随机UUID文件名
     * @param fileName
     * @return
     */
    public static String generateRandonFileName(String fileName){
        //首先获得扩展名,然后生成一个UUID码作为名称，然后加上扩展名
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString()+ext;
    }

    public static String generateRandonFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获得hashcode生成二级目录
     * @param uuidFileName
     * @return
     */
    public static  String generateRandomDir(String uuidFileName){
        int hashCode = uuidFileName.hashCode();//得到它的hashcode编码
        //一级目录
        int d1 = hashCode & 0xf;
        //二级目录
        int d2=(hashCode >> 4) & 0xf;
        return "/"+d1+"/"+d2;
    }

    /**
     * 上传文件
     * @param files
     * @return
     */
    public static List<String> uploadFiles(MultipartFile[] files){
        List<String> picUrlList = new ArrayList<>();
        FtpConfig ftpConfig = ApplicationContextConfig.get(FtpConfig.class);
        try{
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
        }catch (Exception e){
            logger.error("上传失败，ERROR MESSAGE:"+e.getMessage());
            return picUrlList;
        }
    }
}
