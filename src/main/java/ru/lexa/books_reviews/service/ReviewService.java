package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.repository.entity.Review;

import java.util.List;

public interface ReviewService {
    Review create(Review review);

    List<Review> readAll();

    Review read(long id);

    Review update(Review review);

    void delete(long id);
}
