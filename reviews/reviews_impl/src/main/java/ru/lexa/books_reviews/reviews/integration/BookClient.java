package ru.lexa.books_reviews.reviews.integration;

import controller.BookController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "books-client", url = "${books_films.url}")
public interface BookClient extends BookController {;
}
