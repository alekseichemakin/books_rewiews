package ru.lexa.books_reviews.reviews.integration;

import controller.BookController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "books-client", url = "http://localhost:8080")
public interface BookClient extends BookController {;
}
