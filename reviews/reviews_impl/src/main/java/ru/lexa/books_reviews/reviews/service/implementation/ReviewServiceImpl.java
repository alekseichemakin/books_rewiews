package ru.lexa.books_reviews.reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.controller.dto.review.ReviewFilterDTO;
import ru.lexa.books_reviews.reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.reviews.exception.ReviewNotFoundException;
import ru.lexa.books_reviews.reviews.integration.BookFilmClient;
import ru.lexa.books_reviews.reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.reviews.repository.entity.Review;
import ru.lexa.books_reviews.reviews.repository.mapper.ReviewDomainMapper;
import ru.lexa.books_reviews.reviews.repository.specification.ReviewSpecification;
import ru.lexa.books_reviews.reviews.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.reviews.service.ReviewService}
 */
@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private ReviewRepository reviewRepository;

	private ReviewDomainMapper reviewDomainMapper;

	private BookFilmClient bookFilmClient;

	@Transactional
	@Override
	public ReviewDomain create(ReviewDomain review) {
		if (review.getBookId() != null) {
			bookFilmClient.readBook(review.getBookId());
		} else if (review.getFilmId() != null) {
			bookFilmClient.readFilm(review.getFilmId());
		}
		return reviewDomainMapper.reviewToDomain(reviewRepository.save(reviewDomainMapper.domainToReview(review)));
	}

	@Override
	public List<ReviewDomain> readAll() {
		return reviewRepository.findAll().stream().map(reviewDomainMapper::reviewToDomain).collect(Collectors.toList());
	}

	@Override
	public ReviewDomain read(long id) {
		return reviewDomainMapper.reviewToDomain(reviewRepository.findById(id)
				.orElseThrow(() -> {
					throw new ReviewNotFoundException(id);
				}));
	}

	@Transactional
	@Override
	public void delete(long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> {
					throw new ReviewNotFoundException(id);
				});
		reviewRepository.delete(review);
	}

	@Transactional
	@Override
	public ReviewDomain update(ReviewDomain review) {
		ReviewDomain reviewDomain = read(review.getId());
		if (review.getBookId() != null) {
			bookFilmClient.readBook(review.getBookId());
		} else if (review.getFilmId() != null) {
			bookFilmClient.readFilm(review.getFilmId());
		}
		reviewDomain.setText(review.getText());
		reviewDomain.setRating(review.getRating());
		return reviewDomainMapper.reviewToDomain(reviewRepository.save(reviewDomainMapper.domainToReview(reviewDomain)));
	}

	@Override
	public List<ReviewDomain> readAllBooksReviews(ReviewFilterDTO filterDTO) {
		Specification<Review> specification = Specification
				.where(ReviewSpecification.whereBookIdNotNull())
				.and(ReviewSpecification.whereBookId(filterDTO.getBookId()))
				.and(ReviewSpecification.likeText(filterDTO.getText()));
		return reviewRepository.findAll(specification).stream().map(reviewDomainMapper::reviewToDomain).collect(Collectors.toList());
	}

	@Override
	public List<ReviewDomain> readAllFilmsReviews() {
		return reviewRepository.findAllFilmsReviews().stream().map(reviewDomainMapper::reviewToDomain).collect(Collectors.toList());
	}

	@Override
	public Double getBookAverageRating(long bookId) {
		return reviewRepository.getAverageRating(bookId);
	}
}
