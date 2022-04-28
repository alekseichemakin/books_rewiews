package ru.lexa.books_reviews.controller;

import ru.lexa.books_reviews.dto.ReviewDTO;

import java.util.Collection;

public interface ReviewController {
    public ReviewDTO createReview(ReviewDTO dto);

    public Collection<ReviewDTO> readAll();

    public ReviewDTO readReview(long id);

    public ReviewDTO updateReview(ReviewDTO dto, long id);

    public void deleteReview(long id);
}
