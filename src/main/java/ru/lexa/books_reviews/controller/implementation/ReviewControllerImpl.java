package ru.lexa.books_reviews.controller.implementation;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.lexa.books_reviews.controller.ReviewController;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.service.ReviewMappingService;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews/")
@Validated
@AllArgsConstructor
public class ReviewControllerImpl implements ReviewController {

	private ReviewService reviewService;

	private ReviewMappingService reviewMappingService;

	@ApiOperation(value = "Добавить новый отзыв.")
	@PostMapping
	@Override
	public ReviewDTO createReview(@Valid @RequestBody ReviewDTO dto) {
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
	@GetMapping("{id}")
	@Override
	public ReviewDTO readReview(@PathVariable long id) {
		return reviewMappingService.mapToReviewDto(reviewService.read(id));
	}

	@ApiOperation(value = "Изменить отзыв.")
	@PutMapping("{id}")
	@Override
	public ReviewDTO updateReview(@Valid @RequestBody ReviewDTO dto, @PathVariable long id) {
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
