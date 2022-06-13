package ru.lexa.books_reviews.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReviewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReviewsApplication.class, args);
    }
}
