package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.BookReviewRequestDTO;

import java.util.Collection;

@RequestMapping("/api/book/reviews")
public interface BookReviewController {
	@ApiOperation(value = "Добавить новый отзыв к книге.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	BookReviewDTO createReview(@RequestBody BookReviewRequestDTO dto);

	@ApiOperation(value = "Получить все отзывы.")
	@GetMapping
	Collection<BookReviewDTO> readAll();

	@ApiOperation(value = "Получить отзыв.")
	@GetMapping("/{id}")
	BookReviewDTO readReview(@PathVariable long id);

	@ApiOperation(value = "Изменить отзыв.")
	@PutMapping
	BookReviewDTO updateReview(@RequestBody BookReviewDTO dto);

	@ApiOperation(value = "Удалить отзыв.")
	@DeleteMapping("/{id}")
	void deleteReview(@PathVariable long id);
}
