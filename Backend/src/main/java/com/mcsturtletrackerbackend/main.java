package com.mcsturtletrackerbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
@OpenAPIDefinition(info=@Info(title="MCSTurtleTracker Server"))
public class main implements ApplicationContextAware {

    private static ApplicationContext context;

    public static void main(String[] args) {
        try {
            SpringApplication.run(main.class, args);
        } catch (Throwable e) {
            System.out.println(e);
        }

    }



    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        main.context = context;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(Class<T> beanClass)
    {
        return context.getBean(beanClass);
    }

}
