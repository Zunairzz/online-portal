package com.ams.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@ComponentScan({"com.ams"})

@SpringBootApplication

@EnableScheduling
public class AmsServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.ams.service.AmsServicesApplication.class, args);

    }

}