package com.coursemaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.coursemaster.config.properties")
public class CourseMasterApplication{

    public static void main(String[] args) {
        SpringApplication.run(CourseMasterApplication.class, args);
    }

}
