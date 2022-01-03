package com.notes;

import com.azure.spring.autoconfigure.cosmos.CosmosAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = CosmosAutoConfiguration.class)
public class RkordApplication {

    public static void main(String[] args) {
        SpringApplication.run(RkordApplication.class, args);
    }

}