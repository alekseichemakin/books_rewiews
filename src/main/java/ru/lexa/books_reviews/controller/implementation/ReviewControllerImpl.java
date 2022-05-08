package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.ReviewController;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.controller.dto.ReviewResponseDTO;
import ru.lexa.books_reviews.domain.mapper.ReviewMapper;
import ru.lexa.books_reviews.service.ReviewService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.ReviewController}
 */
@RestController
@AllArgsConstructor
public class ReviewControllerImpl implements ReviewController {

	private ReviewService reviewService;

	private ReviewMapper reviewMapper;

	@Override
	public ReviewResponseDTO createReview(ReviewDTO dto) {
		return reviewMapper.reviewToDto(reviewService.create(reviewMapper.dtoToReview(dto)));
	}

	@Override
	public Collection<ReviewResponseDTO> readAll() {
		return reviewService.readAll().stream()
				.map(review -> reviewMapper.reviewToDto(review))
				.collect(Collectors.toList());
	}

	@Override
	public ReviewResponseDTO readReview(long id) {
		return reviewMapper.reviewToDto(reviewService.read(id));
	}

	@Override
	public ReviewResponseDTO updateReview(ReviewResponseDTO dto) {
		return reviewMapper.reviewToDto(reviewService.update(reviewMapper.dtoToReview(dto)));
	}

	@Override
	public void deleteReview(long id) {
		reviewService.delete(id);
	}
}
