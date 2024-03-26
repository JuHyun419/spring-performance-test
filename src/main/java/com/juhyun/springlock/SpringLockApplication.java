package com.juhyun.springlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLockApplication.class, args);
    }

}
