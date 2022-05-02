package ru.lexa.books_reviews.controller.implementation;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.ReviewController;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.service.ReviewMappingService;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@Validated
@AllArgsConstructor
public class ReviewControllerImpl implements ReviewController {

	private ReviewService reviewService;

	private ReviewMappingService reviewMappingService;

	@ApiOperation(value = "Добавить новый отзыв.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public ReviewDTO createReview(@RequestBody ReviewDTO dto) {
		return reviewMappingService.mapToReviewDto(reviewService.create(reviewMappingService.mapToReviewEntity(dto)));
	}

	@ApiOperation(value = "Получить все отзывы.")
	@GetMapping
	@Override
	public Collection<ReviewDTO> readAll() {
		return reviewService.readAll().stream()
				.map(review -> reviewMappingService.mapToReviewDto(review))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Получить отзыв.")
	@GetMapping("/{id}")
	@Override
	public ReviewDTO readReview(@PathVariable long id) {
		return reviewMappingService.mapToReviewDto(reviewService.read(id));
	}

	@ApiOperation(value = "Изменить отзыв.")
	@PutMapping("/{id}")
	@Override
	public ReviewDTO updateReview(@RequestBody ReviewDTO dto, @PathVariable long id) {
		dto.setId(id);
		return reviewMappingService.mapToReviewDto(reviewService.update(reviewMappingService.mapToReviewEntity(dto)));
	}

	@ApiOperation(value = "Удалить отзыв.")
	@DeleteMapping("/{id}")
	@Override
	public void deleteReview(@PathVariable long id) {
		reviewService.delete(id);
	}
}
