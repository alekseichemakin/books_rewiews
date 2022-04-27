package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.dto.ReviewDTO;
import ru.lexa.books_reviews.model.Review;

public interface ReviewMappingService {
    ReviewDTO mapToReviewDto(Review entity);

    Review mapToReviewEntity(ReviewDTO dto);
}
