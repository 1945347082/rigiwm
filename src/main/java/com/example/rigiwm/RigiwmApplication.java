package com.example.rigiwm;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@MapperScan("com.example.rigiwm.mapper")
@ServletComponentScan
@SpringBootApplication
public class RigiwmApplication {

    public static void main(String[] args) {
        SpringApplication.run(RigiwmApplication.class, args);
        log.info("启动服务。。。");
    }

}
