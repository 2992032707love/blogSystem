package com.rts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 16:25
 **/
@SpringBootApplication
@MapperScan("com.rts.mapper")
public class App9401 {
    public static void main(String[] args) {
        SpringApplication.run(App9401.class,args);
    }
}