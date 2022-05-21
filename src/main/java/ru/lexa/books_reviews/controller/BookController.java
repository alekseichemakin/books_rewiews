package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.book.BookRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Контроллер принимающий запросы для книг
 */
@RequestMapping("/api/books")
@Validated
public interface BookController {

	@ApiOperation(value = "Добавить новую книгу.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	BookResponseDTO createBook(@RequestBody @Valid BookRequestDTO dto);

	@ApiOperation(value = "Получить все книги.")
	@GetMapping
	Collection<BookResponseDTO> readAll(@RequestParam(required = false) Integer page,
	                                    @RequestParam(required = false) Integer pageSize,
	                                    @RequestParam(required = false) String author,
	                                    @RequestParam(required = false) String description,
	                                    @RequestParam(required = false) String name,
	                                    @RequestParam(required = false) String reviewText,
	                                    @RequestParam(required = false) Double maxRating);

	@ApiOperation(value = "Получить книгу.")
	@GetMapping("/{id}")
	BookResponseDTO readBook(@PathVariable long id);

	@ApiOperation(value = "Изменить книгу.")
	@PutMapping
	BookResponseDTO updateBook(@RequestBody BookDTO dto);

	@ApiOperation(value = "Удалить книгу.")
	@DeleteMapping("/{id}")
	void deleteBook(@PathVariable long id);

	@ApiOperation(value = "Получить отзывы к книге.")
	@GetMapping("/{id}/reviews")
	Collection<BookReviewDTO> getReviews(@PathVariable long id);

	@ApiOperation(value = "Получить среднюю оценку.")
	@GetMapping("/{id}/averageRating")
	double getAverage(@PathVariable long id);

	//TODO improve desc
	@ApiOperation(value = "Получить автора книги.")
	@GetMapping("/{id}/authors")
	Collection<AuthorDTO> getAuthors(@PathVariable long id);
}
