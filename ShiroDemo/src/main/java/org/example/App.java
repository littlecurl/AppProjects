package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这里需要指明 @SpringBootApplication 注解
 * 使用其内部 @SpringBootConfiguration 注解
 * 否则无法进行单元测试
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
