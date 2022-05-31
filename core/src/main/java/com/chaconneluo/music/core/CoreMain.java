package com.chaconneluo.music.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ChaconneLuo
 */
@SpringBootApplication
@EnableFeignClients
public class CoreMain {
    public static void main(String[] args) {
        SpringApplication.run(CoreMain.class, args);
    }
}
