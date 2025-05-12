package com.sale.hot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SaleUserBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaleUserBeApplication.class, args);
    }

}
