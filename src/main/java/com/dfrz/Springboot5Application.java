package com.dfrz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dfrz.mapper")
public class Springboot5Application {
    public static void main(String[] args) {
        SpringApplication.run(Springboot5Application.class, args);
    }
}
