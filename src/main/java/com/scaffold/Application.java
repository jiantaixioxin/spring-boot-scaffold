package com.scaffold;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.scaffold.dao.db")
public class Application {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        SpringApplication.run(Application.class,args);
        long end=System.currentTimeMillis();
        long useTime=end-start;
        System.out.println("***********************************************");
        System.out.println("spring-boot-scaffold start success!");
        System.out.println("use time："+useTime+"ms");
        System.out.println("***********************************************");
    }
}