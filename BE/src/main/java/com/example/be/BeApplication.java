package com.example.be;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
@MapperScan("com.example.be.mapper")
public class BeApplication {
    public static Map<String, Integer> tokenMap = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(BeApplication.class, args);
    }

    @GetMapping
    public String health(){
        return "SUCCESS";
    }

}
