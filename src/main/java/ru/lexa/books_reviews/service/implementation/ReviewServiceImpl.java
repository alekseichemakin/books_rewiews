package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.FilmNotFoundException;
import ru.lexa.books_reviews.exception.ReviewNotFoundException;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.ReviewService}
 */
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private ReviewRepository reviewRepository;

//	private BookService bookService;

	private FilmService filmService;

	@Override
	public Review create(Review review) {
//		if (review.getBook() != null && bookService.read(review.getBook().getId()) == null) {
//			throw new BookNotFoundException(review.getBook().getId());
//		}
		if (review.getFilm() != null && filmService.read(review.getFilm().getId()) == null) {
			throw new FilmNotFoundException(review.getFilm().getId());
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
				.orElseThrow(() -> {throw new ReviewNotFoundException(id);});
	}

	@Override
	public void delete(long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> {throw new ReviewNotFoundException(id);});
		reviewRepository.delete(review);
	}

	@Override
	public Review update(@Valid Review review) {
		reviewRepository.findById(review.getId())
				.orElseThrow(() -> {throw new ReviewNotFoundException(review.getId());});
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> readAllBooksReviews() {
		return reviewRepository.findAllBooksReviews();
	}

	@Override
	public List<Review> readAllFilmsReviews() {
		return reviewRepository.findAllFilmsReviews();
	}

	@Override
	public Double getBookAverageRating(long bookId) {
		return reviewRepository.getAverageRating(bookId);
	}
}
