package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.repository.entity.Review;

public interface ReviewMappingService {
    ReviewDTO mapToReviewDto(Review entity);

    Review mapToReviewEntity(ReviewDTO dto);
}
