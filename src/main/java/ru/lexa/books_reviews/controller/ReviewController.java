package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.controller.dto.ReviewResponseDTO;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/api/reviews")
@Validated
public interface ReviewController {
	@ApiOperation(value = "Добавить новый отзыв.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ReviewResponseDTO createReview(@RequestBody @Valid ReviewDTO dto);

	@ApiOperation(value = "Получить все отзывы.")
	@GetMapping
	Collection<ReviewResponseDTO> readAll();

	@ApiOperation(value = "Получить отзыв.")
	@GetMapping("/{id}")
	ReviewResponseDTO readReview(@PathVariable long id);

	@ApiOperation(value = "Изменить отзыв.")
	@PutMapping("/{id}")
	ReviewResponseDTO updateReview(@RequestBody @Valid ReviewDTO dto, @PathVariable long id);

	@ApiOperation(value = "Удалить отзыв.")
	@DeleteMapping("/{id}")
	void deleteReview(@PathVariable long id);
}
