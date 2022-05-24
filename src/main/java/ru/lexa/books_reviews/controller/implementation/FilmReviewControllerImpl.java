package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.FilmReviewController;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewRequestDTO;
import ru.lexa.books_reviews.controller.mapper.FilmReviewMapper;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.service.FilmService;
import ru.lexa.books_reviews.service.ReviewService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.FilmReviewController}
 */
@AllArgsConstructor
@RestController
public class FilmReviewControllerImpl implements FilmReviewController {

	private ReviewService reviewService;

	private FilmService filmService;

	private FilmReviewMapper reviewMapper;

	@Override
	public FilmReviewDTO createReview(FilmReviewRequestDTO dto) {
		Film film = filmService.read(dto.getFilmId());
		return reviewMapper.reviewToDto(reviewService.create(reviewMapper.dtoToReview(dto, film)));
	}

	@Override
	public Collection<FilmReviewDTO> readAll() {
		return reviewService.readAllFilmsReviews().stream()
				.map(review -> reviewMapper.reviewToDto(review))
				.collect(Collectors.toList());
	}

	@Override
	public FilmReviewDTO readReview(long id) {
		return reviewMapper.reviewToDto(reviewService.read(id));
	}

	@Override
	public FilmReviewDTO updateReview(FilmReviewDTO dto) {
		Film film = filmService.read(dto.getFilmId());
		return reviewMapper.reviewToDto(reviewService.update(reviewMapper.dtoToReview(dto, film)));

	}

	@Override
	public void deleteReview(long id) {
		reviewService.delete(id);
	}
}
