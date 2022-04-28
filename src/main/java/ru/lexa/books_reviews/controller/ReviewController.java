package ru.lexa.books_reviews.controller;

import ru.lexa.books_reviews.dto.ReviewDTO;

import java.util.Collection;

public interface ReviewController {
    ReviewDTO createReview(ReviewDTO dto);

    Collection<ReviewDTO> readAll();

    ReviewDTO readReview(long id);

    ReviewDTO updateReview(ReviewDTO dto, long id);

    void deleteReview(long id);
}
