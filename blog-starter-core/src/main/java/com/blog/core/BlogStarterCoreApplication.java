package com.blog.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.blog.core","com.blog.cache","com.blog.swagger"})
@SpringBootApplication
public class BlogStarterCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogStarterCoreApplication.class, args);
    }

}
