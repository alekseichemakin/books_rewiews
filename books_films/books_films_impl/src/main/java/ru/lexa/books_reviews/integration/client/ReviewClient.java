package ru.lexa.books_reviews.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.lexa.books_reviews.controller.BookReviewController;

/**
 * Клиент отзывов отправляющий запросы на Review сервис
 */
@FeignClient(value = "reviews")
public interface ReviewClient extends BookReviewController {
}
