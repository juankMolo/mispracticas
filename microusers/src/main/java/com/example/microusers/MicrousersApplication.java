package com.example.microusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MicrousersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrousersApplication.class, args);
    }
}

