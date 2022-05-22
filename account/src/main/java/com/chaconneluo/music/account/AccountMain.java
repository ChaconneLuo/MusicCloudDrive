package com.chaconneluo.music.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ChaconneLuo
 */

// VM-OPTIONS : --add-opens java.base/java.lang.invoke=ALL-UNNAMED
@SpringBootApplication
public class AccountMain {
    public static void main(String[] args) {
        SpringApplication.run(AccountMain.class, args);
    }
}
