package com.test.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.test.mapper")
@ComponentScan("com.test")
public class Application 
{
    public static void main( String[] args ) 
    {
        SpringApplication.run(Application.class, args);
    }
}