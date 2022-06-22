package ru.lexa.books_reviews.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;

import java.util.Collection;

@FeignClient(value = "reviews")
public interface ReviewClient {
        @GetMapping("/api/books/reviews/averageRating/{bookId}")
        Double getAverageBookRating(@PathVariable("bookId") long bookId);

        @GetMapping("/api/books/reviews")
        Collection<BookReviewDTO> getReviewsForBook(@RequestParam("bookId") long bookId);

        @GetMapping("/api/books/reviews")
        Collection<BookReviewDTO> getReviewsForText(@RequestParam("text") String text);
}
