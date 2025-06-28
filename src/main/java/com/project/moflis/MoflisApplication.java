package com.project.moflis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoflisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoflisApplication.class, args);
    }

}
