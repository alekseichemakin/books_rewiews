package ru.lexa.books_reviews.integration;

import org.springframework.cloud.openfeign.FeignClient;
import ru.lexa.books_reviews.controller.BookReviewController;

@FeignClient(value = "reviews")
public interface ReviewClient extends BookReviewController {
}
