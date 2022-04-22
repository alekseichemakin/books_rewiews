package ru.lexa.books_reviews.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.ReviewNotFoundException;
import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.model.Review;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookService bookService;

    @Override
    public Review create(Review review) {
        if (bookService.read(review.getBook_id()) == null)
            throw new BookNotFoundException();
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> readAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review read(long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null)
            throw new ReviewNotFoundException();
        return review;
    }

    @Override
    public void delete(long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null)
            throw new ReviewNotFoundException();
        reviewRepository.delete(review);
    }

    @Override
    public Review update(@Valid Review review) {
        Review updReview = reviewRepository.findById(review.getId()).orElse(null);

        if (updReview == null)
            throw new ReviewNotFoundException();
        updReview.setRating(review.getRating());
        updReview.setText(review.getText());
        reviewRepository.save(updReview);
        return updReview;
    }


}
