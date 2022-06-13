package ru.lexa.books_reviews.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerClass {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerClass.class, args);
    }
}
