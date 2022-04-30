package ru.lexa.books_reviews.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewMappingService;

@Service
public class ReviewMappingServiceImpl implements ReviewMappingService {

    @Autowired
    BookService bookService;

    @Override
    public ReviewDTO mapToReviewDto(Review entity) {
        ReviewDTO dto = new ReviewDTO();
        if (entity != null) {
            dto.setRating(entity.getRating());
            dto.setText(entity.getText());
            dto.setBook_id(entity.getBook().getId());
            dto.setId(entity.getId());
        }
        return dto;
    }

    @Override
    public Review mapToReviewEntity(ReviewDTO dto) {
        Review review = new Review();
        if (dto != null) {
            review.setBook(bookService.read(dto.getBook_id()));
            review.setRating(dto.getRating());
            review.setText(dto.getText());
            review.setId(dto.getId());
        }
        return review;
    }
}
