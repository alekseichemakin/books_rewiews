package ru.lexa.books_reviews.integration.service;

import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.exception.AuthorNotFoundException;
import ru.lexa.books_reviews.repository.entity.Author;

import java.util.Collection;
import java.util.Set;

/**
 * Сервис работающий с клиентом reviews
 */
public interface ReviewsService {
    /**
     * Возвращает средний рейтинг книги
     *
     * @param bookId - id книги
     */
    Double getBookAverageRating(Long bookId);

    /**
     * Возвращает средний рейтинг автора
     *
     * @param author - автор
     */
    Double getAuthorAverageRating(Author author);

    /**
     * Возвращает отзывы к книге
     *
     * @param bookId - id книги
     */
    Collection<BookReviewDTO> getBookReviews(Long bookId);

    /**
     * Возвращает список id книг в отзывах которых содержиться text
     *
     * @param text - текст содержащийся в отзыве
     */
    Set<Long> getBookIdsWhereReviewText(String text);
}
