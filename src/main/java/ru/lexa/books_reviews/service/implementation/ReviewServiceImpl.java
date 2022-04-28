package ru.lexa.books_reviews.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.model.Review;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookService bookService;

    @Override
    public Review create(Review review) {
        if (bookService.read(review.getBook().getId()) == null) {
            throw new InputErrorException("Нет книги с данным id");
        }
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> readAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review read(long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new InputErrorException("Нет отзыва с данным id"));
    }

    @Override
    public void delete(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new InputErrorException("Нет отзыва с данным id"));
        reviewRepository.delete(review);
    }

    @Override
    public Review update(@Valid Review review) {
        Review updReview = reviewRepository.findById(review.getId())
                .orElseThrow(() -> new InputErrorException("Нет отзыва с данным id"));
        updReview.setRating(review.getRating());
        updReview.setText(review.getText() == null ? updReview.getText() : review.getText());
        return reviewRepository.save(updReview);
    }
}
