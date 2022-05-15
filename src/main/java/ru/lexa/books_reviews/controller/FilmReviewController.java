package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewRequestDTO;

import java.util.Collection;

@RequestMapping("/api/film/reviews")
public interface FilmReviewController {
	@ApiOperation(value = "Добавить новый отзыв к фильму.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	FilmReviewDTO createReview(@RequestBody FilmReviewRequestDTO dto);

	@ApiOperation(value = "Получить все отзывы.")
	@GetMapping
	Collection<FilmReviewDTO> readAll();

	@ApiOperation(value = "Получить отзыв.")
	@GetMapping("/{id}")
	FilmReviewDTO readReview(@PathVariable long id);

	@ApiOperation(value = "Изменить отзыв.")
	@PutMapping
	FilmReviewDTO updateReview(@RequestBody FilmReviewDTO dto);

	@ApiOperation(value = "Удалить отзыв.")
	@DeleteMapping("/{id}")
	void deleteReview(@PathVariable long id);
}
