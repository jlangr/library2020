package com.langrsoft.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


// TODO rework package structure to be com.langrsoft
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"api"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
