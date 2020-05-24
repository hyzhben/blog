package com.blog.core.util;

import com.blog.core.config.FtpConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * ftp上传工具类
 */
public class FtpUtil {

    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    public static  String pictureUploadByConfig(FtpConfig ftpConfig, String picNewName, String picSavePath, InputStream inputStream){
        logger.info("pictureUploadByConfig.....");
        String picHttpPath = null;
        boolean flag = uploadFile(ftpConfig.getAddress(),ftpConfig.getPort(),ftpConfig.getUsername(),ftpConfig.getPassword(),ftpConfig.getBasePath(),picSavePath,picNewName,inputStream);
        if(!flag){
            return picHttpPath;
        }
        picHttpPath = picSavePath+"/"+picNewName;
        logger.info("【picHttpPath】"+picHttpPath);
        return picHttpPath;
    }

    /**
     * Description: 向FTP服务器上传文件
     * @param host
     *          FTP服务器hostname
     * @param ftpPort
     *          FTP服务器端口
     * @param username
     *          FTP登录账号
     * @param password
     *          FTP登录密码
     * @param basePath
     *          FTP服务器基础目录
     * @param filePath
     *          FTP服务器文件存放路径
     * @param filename
     *          上传到FTP服务器上的文件名
     * @param inputStream
     *      输入流
     * @return
     *      成功返回true，否则返回false
     */
    public static boolean uploadFile(String host,String ftpPort,String username,String password,String basePath,String filePath,String filename,InputStream inputStream){
            int port = Integer.parseInt(ftpPort);
            boolean result = false;
        FTPClient ftp = new FTPClient();
        try{
            int reply;
            //如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.connect(host,port);//连接FTP服务器
            ftp.login(username,password);
            reply = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if(!ftp.changeWorkingDirectory(basePath+filePath)){
                //如果目录不存在创建目录
                String [] dirs = filePath.split("/");
                String tempPath = basePath;
                for(String dir : dirs){
                    if(null == dir || "".equals(dir))
                        continue;
                    tempPath+="/"+dir;
                    if (!ftp.changeWorkingDirectory(tempPath)){
                        if(!ftp.makeDirectory(tempPath)){
                            return result;
                        }else{
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();//这个设置允许被动连接--访问远程ftp时需要
            //上传文件
            if(!ftp.storeFile(filename,inputStream)){
                return result;
            }
            inputStream.close();
            ftp.logout();
            result = true;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(ftp.isConnected()){
                try{
                    ftp.disconnect();
                }catch (IOException e){

                }
            }
        }
        return result;
    }
}
