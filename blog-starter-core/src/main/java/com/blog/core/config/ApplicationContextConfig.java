package com.blog.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextConfig implements ApplicationContextAware {

    //定义静态的ApplicationContext成员对象
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextConfig.applicationContext = applicationContext;
    }
    //定义get方法，参数为class，调用上下文的getBean方法获取容器中的指定对象
    public static <T> T get(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    //同上，参数为对象名
    public static Object get(String name) {
        return applicationContext.getBean(name);
    }
}
