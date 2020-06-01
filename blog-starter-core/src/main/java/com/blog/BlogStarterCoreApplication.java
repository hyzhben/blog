package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@ComponentScan({"com.blog.core","com.cn.blog.cache","com.cn.blog.swagger"})
@SpringBootApplication
@PropertySource(value = {"classpath:application-redis.properties","classpath:application-ftp.properties"})
public class BlogStarterCoreApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BlogStarterCoreApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //Application的类名
        return application.sources(BlogStarterCoreApplication.class);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
