package ru.lexa.books_reviews.integration.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.ReviewFilterDTO;
import ru.lexa.books_reviews.integration.client.ReviewClient;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.integration.service.ReviewsService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация ReviewService
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewsService {

    private final ReviewClient reviewClient;

    @Override
    public Double getBookAverageRating(Long bookId) {
        Double rating = reviewClient.averageBookRating(bookId);
        return rating == null ? 0 : rating;
    }

    @Override
    public Double getAuthorAverageRating(Author author) {
        return author.getBooks().stream()
                .map(book -> getBookAverageRating(book.getBook().getId()))
                .mapToDouble(Double::doubleValue)
                .sum() / author.getBooks().size();
    }

    @Override
    public Collection<BookReviewDTO> getBookReviews(Long bookId) {
        return reviewClient.readAll(new ReviewFilterDTO(null, bookId));
    }

    @Override
    public Set<Long> getBookIdsWhereReviewText(String text) {
        return reviewClient.readAll(new ReviewFilterDTO(text, null)).stream()
                .map(BookReviewRequestDTO::getBookId)
                .collect(Collectors.toSet());
    }
}
