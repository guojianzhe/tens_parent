package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import util.IdWorker;

@SpringBootApplication
@EnableTransactionManagement
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }

    @Bean
    //注入id生成器
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}