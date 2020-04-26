package com.blog.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Mybatis相关配置
 */
@Configuration
public class MyBatisConfig {

    /**
     * Mapper扫描谁知，自动扫描将Mapper接口生成代理注入到Spring
     */
    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        //注意这里的扫描路径：1.不要扫描自定义的Mapper；2.定义的路径不要扫描到tk.mybatis.mapper（如定义**.mapper）.
        //两个做法都会导致扫描的到tk.mybatis的Mapper，就会产生重复定义的报错
        mapperScannerConfigurer.setBasePackage("**.blog.**.mapper");
        return mapperScannerConfigurer;
    }
}
