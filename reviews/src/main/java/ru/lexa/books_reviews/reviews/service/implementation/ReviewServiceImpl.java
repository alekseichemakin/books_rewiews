package ru.lexa.books_reviews.reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.reviews.controller.mapper.BookMapper;
import ru.lexa.books_reviews.reviews.controller.mapper.FilmMapper;
import ru.lexa.books_reviews.reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.reviews.exception.InputErrorException;
import ru.lexa.books_reviews.reviews.exception.ReviewNotFoundException;
import ru.lexa.books_reviews.reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.reviews.repository.entity.Book;
import ru.lexa.books_reviews.reviews.repository.entity.Film;
import ru.lexa.books_reviews.reviews.repository.entity.Review;
import ru.lexa.books_reviews.reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.reviews.repository.mapper.ReviewDomainMapper;
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

	private RestTemplate restTemplate;

	private BookDomainMapper bookDomainMapper;

	private BookMapper bookMapper;

	private FilmDomainMapper filmDomainMapper;

	private FilmMapper filmMapper;

	@Transactional
	@Override
	public ReviewDomain create(ReviewDomain review) {
		if (review.getBookId() != null) {
			review.setBook(getBookFromRest(review.getBookId()));
		} else if (review.getFilmId() != null) {
			review.setFilm(getFilmFromRest(review.getFilmId()));
		}
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
				.orElseThrow(() -> {
					throw new ReviewNotFoundException(id);
				}));
		return setIds(review);
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
			reviewDomain.setBook(getBookFromRest(review.getBookId()));
		} else if (review.getFilmId() != null) {
			reviewDomain.setFilm(getFilmFromRest(review.getFilmId()));
		}
		reviewDomain.setText(review.getText());
		reviewDomain.setRating(review.getRating());
		reviewDomain = reviewDomainMapper.reviewToDomain(reviewRepository.save(reviewDomainMapper.domainToReview(reviewDomain)));
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

	private Book getBookFromRest(Long bookId) {
		BookResponseDTO bookResponseDTO;
		try {
			bookResponseDTO = restTemplate.getForObject("http://COMMON/api/books/{id}",
					BookResponseDTO.class,
					bookId);
		} catch (HttpClientErrorException e) {
			throw new InputErrorException(e.getMessage());
		}
		return bookDomainMapper.domainToBook(bookMapper.dtoToBook(bookResponseDTO));
	}

	private Film getFilmFromRest(Long filmId) {
		FilmDTO filmDTO = restTemplate.getForObject("http://COMMON/api/films/{id}",
				FilmDTO.class,
				filmId);
		return filmDomainMapper.domainToFilm(filmMapper.dtoToFilm(filmDTO));
	}
}
