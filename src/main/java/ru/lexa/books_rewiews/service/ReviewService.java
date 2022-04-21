package ru.lexa.books_rewiews.service;

import ru.lexa.books_rewiews.model.Review;

import java.util.List;

public interface ReviewService {
    Review create(Review review);

    List<Review> readAll();

    Review read(long id);

    Review update(Review review);

    Review updateReviewText(Review review);

    Review updateReviewRating(Review review);

    boolean delete(long id);
}
