package com.blog.core.constants;

import com.google.common.base.Charsets;

import java.nio.charset.Charset;

/**
 * 系统级常量类
 *
 * @version 1.0
 * @author bojiangzhou 2017-12-28
 */
public class Constants {

    //公钥
    public static final String publickey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnfnWpYORbGiP03y/LOTKbXAOgz6k1Ul8x5gMiOSAgnCAidbyoToQs+xYEr+TSu9RrRa/akP17ORYZ2auowmU4Y52yVn37ai01dqs3Ca96dTp6dP4V1hdEuh1b91LXJtipN/yY4Q/RX+0bJrKeCSEnHIaJPYdrOQord5Sq1XBfS6h3Qsz5a4QSHOaevkhX30OdyRUjy4lncZ8+rn1cSC9/l8Fv8b5XVpILrEQ9P3Mm1Y2KyCdZtg1EELgk7QSDZaycfqiC6vLCDkx0L+aGM3Bg8IFC1Fm0P6B1P4G8v8UFur5Lw2IF7a0ELwnTL5/55CQ/kaF+j6q5mIR0ULPx92MBwIDAQAB-----END PUBLIC KEY-----";

    public static final String APP_NAME = "sunny";

    /**
     * 系统编码
     */
    public static final Charset CHARSET = Charsets.UTF_8;

    /**
     * 标识：是/否、启用/禁用等
     */
    public interface Flag {

        Integer YES = 1;

        Integer NO = 0;
    }

    /**
     * 操作类型
     */
    public interface Operation {
        /**
         * 添加
         */
        String ADD = "add";
        /**
         * 更新
         */
        String UPDATE = "update";
        /**
         * 删除
         */
        String DELETE = "delete";
    }

    /**
     * 性别
     */
    public interface Sex {
        /**
         * 男
         */
        Integer MALE = 1;
        /**
         * 女
         */
        Integer FEMALE = 0;
    }

}
