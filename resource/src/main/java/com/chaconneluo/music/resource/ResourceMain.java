package com.chaconneluo.music.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ChaconneLuo
 */

// VM-OPTIONS: --add-opens java.base/java.nio.file=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/jdk.internal.ref=ALL-UNNAMED --add-opens java.base/java.lang.ref=ALL-UNNAMED

@SpringBootApplication
public class ResourceMain {
    public static void main(String[] args) {
        SpringApplication.run(ResourceMain.class, args);
    }
}
