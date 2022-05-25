package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.FilmNotFoundException;
import ru.lexa.books_reviews.exception.ReviewNotFoundException;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.repository.mapper.ReviewDomainMapper;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.ReviewService}
 */
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private ReviewRepository reviewRepository;

	private ReviewDomainMapper reviewDomainMapper;

	@Override
	public ReviewDomain create(ReviewDomain review) {
		review = reviewDomainMapper.reviewToDomain(reviewRepository.save(reviewDomainMapper.domainToReview(review)));
		return setIds(review);
	}

	@Override
	public List<ReviewDomain> readAll() {
		List<ReviewDomain> reviewDomains = reviewRepository.findAll().stream().map(reviewDomainMapper::reviewToDomain).collect(Collectors.toList());
		reviewDomains.forEach(this::setIds);
		return reviewDomains;
	}

	@Override
	public ReviewDomain read(long id) {
		ReviewDomain review = reviewDomainMapper.reviewToDomain(reviewRepository.findById(id)
				.orElseThrow(() -> {throw new ReviewNotFoundException(id);}));
		return setIds(review);
	}

	@Override
	public void delete(long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> {throw new ReviewNotFoundException(id);});
		reviewRepository.delete(review);
	}

	@Override
	public ReviewDomain update(ReviewDomain review) {
		ReviewDomain reviewDomain = read(review.getId());
		reviewDomain.setText(review.getText());
		reviewDomain.setRating(review.getRating());
		reviewDomain = reviewDomainMapper.reviewToDomain(reviewRepository.save(reviewDomainMapper.domainToReview(review)));
		return setIds(reviewDomain);
	}

	@Override
	public List<ReviewDomain> readAllBooksReviews() {
		return reviewRepository.findAllBooksReviews().stream().map(reviewDomainMapper::reviewToDomain).map(this::setIds).collect(Collectors.toList());
	}

	@Override
	public List<ReviewDomain> readAllFilmsReviews() {
		return reviewRepository.findAllFilmsReviews().stream().map(reviewDomainMapper::reviewToDomain).map(this::setIds).collect(Collectors.toList());
	}

	@Override
	public Double getBookAverageRating(long bookId) {
		return reviewRepository.getAverageRating(bookId);
	}

	private ReviewDomain setIds(ReviewDomain review) {
		if (review.getBook() != null) {
			review.setBookId(review.getBook().getId());
		} else if (review.getFilm() != null) {
			review.setFilmId(review.getFilm().getId());
		}
		return review;
	}
}
