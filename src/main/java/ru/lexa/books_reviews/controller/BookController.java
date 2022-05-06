package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.controller.dto.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.ReviewResponseDTO;

import javax.validation.Valid;
import java.util.Collection;


@RequestMapping("/api/books")
@Validated
public interface BookController {

	@ApiOperation(value = "Добавить новую книгу.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	BookResponseDTO createBook(@RequestBody @Valid BookDTO dto);

	@ApiOperation(value = "Получить все книги.")
	@GetMapping
	Collection<BookResponseDTO> readAll(@RequestParam(required = false) String author,
	                            @RequestParam(required = false) String description,
	                            @RequestParam(required = false) String name,
	                            @RequestParam(required = false) String reviewText);

	@ApiOperation(value = "Получить книгу.")
	@GetMapping("/{id}")
	BookResponseDTO readBook(@PathVariable  long id);

	@ApiOperation(value = "Изменить книгу.")
	@PutMapping
	BookResponseDTO updateBook(@RequestBody BookResponseDTO dto);

	@ApiOperation(value = "Удалить книгу.")
	@DeleteMapping("/{id}")
	void deleteBook(@PathVariable long id);

	@ApiOperation(value = "Получить отзывы к книге.")
	@GetMapping("/{id}/reviews")
	Collection<ReviewResponseDTO> getReviews(@PathVariable long id);

	@ApiOperation(value = "Получить среднюю оценку.")
	@GetMapping("/{id}/averageRating")
	double getAverage(@PathVariable long id);
}
