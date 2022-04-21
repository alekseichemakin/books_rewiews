package ru.lexa.books_rewiews.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.books_rewiews.model.Review;
import ru.lexa.books_rewiews.repository.ReviewRepository;
import ru.lexa.books_rewiews.service.BookService;
import ru.lexa.books_rewiews.service.ReviewService;

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
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null)
            return false;
        reviewRepository.delete(review);
        return true;
    }

    @Override
    public Review update(@Valid Review review) {
        return uniUpdate(review, (currentReview) -> {
            currentReview.setText(review.getText());
            currentReview.setRating(review.getRating());
        });
    }

    @Override
    public Review updateReviewText(Review review) {
        return uniUpdate(review, (currentReview) -> currentReview.setText(review.getText()));
    }

    @Override
    public Review updateReviewRating(@Valid Review review) {
        return uniUpdate(review, (currentReview) -> currentReview.setRating(review.getRating()));
    }

    private Review uniUpdate(Review review, Consumer<Review> consumer) {
        Review currentReview = reviewRepository.findById(review.getId()).orElse(null);
        if (currentReview != null) {
            consumer.accept(currentReview);
        }
        return currentReview;
    }
}
