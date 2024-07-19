package com.ACC.SpringApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ACC.SpringApp.repository")
public class SpringAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringAppApplication.class, args);
    }
}
