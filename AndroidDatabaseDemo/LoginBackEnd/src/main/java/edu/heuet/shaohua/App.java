package edu.heuet.shaohua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"edu.heuet.shaohua"})
@RestController
@MapperScan("edu.heuet.shaohua.dao")
public class App  {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class);
    }
}
