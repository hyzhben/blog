package com.blog.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan({"com.blog.core","com.blog.cache","com.blog.swagger"})
@SpringBootApplication
@PropertySource(value = {"classpath:application-redis.properties"})
public class BlogStarterCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogStarterCoreApplication.class, args);
    }

}
