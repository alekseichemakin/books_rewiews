package ru.lexa.books_reviews.reviews.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.controller.dto.review.ReviewFilterDTO;
import ru.lexa.books_reviews.reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.reviews.exception.ReviewNotFoundException;
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
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	private final ReviewDomainMapper reviewDomainMapper;

	private final AmqpTemplate rabbitTemplate;

	@Value("${spring.rabbitmq.exchange}")
	private String EXCHANGE;
	@Value("${spring.rabbitmq.routing-key.clearBookCache}")
	private String CLEAR_BOOK_CACHE;

	@CacheEvict(value = "bookRating", key = "#review.bookId", condition="#review.bookId!=null")
	@Transactional
	@Override
	public ReviewDomain create(ReviewDomain review) {
		if (review.getBookId() != null) {
			rabbitTemplate.convertAndSend(EXCHANGE, CLEAR_BOOK_CACHE, review.getBookId());
		}

		return reviewDomainMapper.reviewToDomain(reviewRepository.save(reviewDomainMapper.domainToReview(review)));
	}

	@Override
	public List<ReviewDomain> readAll() {
		return reviewRepository.findAll().stream().map(reviewDomainMapper::reviewToDomain).collect(Collectors.toList());
	}

	@Cacheable(value = "reviews", key = "#id")
	@Override
	public ReviewDomain read(long id) {
		return reviewDomainMapper.reviewToDomain(reviewRepository.findById(id)
				.orElseThrow(() -> {
					throw new ReviewNotFoundException(id);
				}));
	}

	@Caching(
			evict = {
					@CacheEvict(value = "reviews", key = "#id"),
					@CacheEvict(value = "bookRating", allEntries = true)
			}
	)
	@Transactional
	@Override
	public void delete(long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> {
					throw new ReviewNotFoundException(id);
				});
		reviewRepository.delete(review);
		rabbitTemplate.convertAndSend(EXCHANGE, CLEAR_BOOK_CACHE, review.getBookId());
	}


	@Caching(
			evict = @CacheEvict(value = "bookRating", allEntries = true),
			put = @CachePut(value = "reviews", key = "#review.id")
	)
	@Transactional
	@Override
	public ReviewDomain update(ReviewDomain review) {
		ReviewDomain reviewDomain = read(review.getId());
		if (review.getBookId() != null) {
			rabbitTemplate.convertAndSend(EXCHANGE, CLEAR_BOOK_CACHE, review.getBookId());
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

	@Cacheable(value = "bookRating", key = "#bookId")
	@Override
	public Double getBookAverageRating(long bookId) {
		return reviewRepository.getAverageRating(bookId);
	}

	@Transactional
	@Override
	public void deleteReviewsForBook(long bookId) {
		reviewRepository.deleteAllByBookId(bookId);
	}

	@Transactional
	@Override
	public void deleteReviewsForFilm(long filmId) {
		reviewRepository.deleteAllByFilmId(filmId);
	}
}
