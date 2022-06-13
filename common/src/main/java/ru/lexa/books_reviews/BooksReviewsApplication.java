package ru.lexa.books_reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BooksReviewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksReviewsApplication.class, args);
    }
}
