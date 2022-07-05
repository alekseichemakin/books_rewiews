package ru.lexa.books_reviews.controller;

import feign.Headers;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.ReviewFilterDTO;

import java.util.Collection;

/**
 * Контроллер принимающий запросы для отзывов книг
 */
@RequestMapping("/api/books/reviews")
public interface BookReviewController {
	@ApiOperation(value = "Добавить новый отзыв к книге.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	BookReviewDTO createReview(@RequestBody BookReviewRequestDTO dto);

	@ApiOperation(value = "Поиск отывов по параметрам.")
	@GetMapping
	Collection<BookReviewDTO> readAll(@SpringQueryMap ReviewFilterDTO reviewFilterDTO);

	@ApiOperation(value = "Получить отзыв.")
	@GetMapping("/{id}")
	BookReviewDTO readReview(@PathVariable("id") long id);

	@ApiOperation(value = "Изменить отзыв.")
	@PutMapping
	BookReviewDTO updateReview(@RequestBody BookReviewDTO dto);

	@ApiOperation(value = "Удалить отзыв.")
	@DeleteMapping("/{id}")
	void deleteReview(@PathVariable("id") long id);

	@ApiOperation(value = "Получить средний ретинг книги.")
	@GetMapping("/averageRating/{bookId}")
	Double averageBookRating(@PathVariable("bookId") long bookId);
}
