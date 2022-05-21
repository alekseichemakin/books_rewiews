package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;

import java.util.Collection;

/**
 * Контроллер принимающий запросы для автора
 */
@RequestMapping("/api/authors")
public interface AuthorController {

	@ApiOperation(value = "Добавить нового автора.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	AuthorDTO createAuthor(@RequestBody AuthorRequestDTO dto);

	@ApiOperation(value = "Получить всех авторов.")
	@GetMapping
	Collection<AuthorDTO> readAll(@RequestParam(required = false) Integer page,
	                              @RequestParam(required = false) Integer pageSize,
	                              @RequestParam(required = false) Double maxRating,
	                              @RequestParam(required = false) String name,
	                              @RequestParam(required = false) String book,
	                              @RequestParam(required = false) String film);

	@ApiOperation(value = "Получить автора.")
	@GetMapping("/{id}")
	AuthorDTO readAuthor(@PathVariable long id);

	@ApiOperation(value = "Изменить автора.")
	@PutMapping
	AuthorDTO updateAuthor(@RequestBody AuthorDTO dto);

	@ApiOperation(value = "Удалить автора.")
	@DeleteMapping("/{id}")
	void deleteAuthor(@PathVariable long id);

	@ApiOperation(value = "Получить книги автора.")
	@GetMapping("/{id}/books")
	Collection<BookResponseDTO> readBooks(@PathVariable long id);

	@ApiOperation(value = "Получить фильмы автора.")
	@GetMapping("/{id}/films")
	Collection<FilmDTO> readFilms(@PathVariable long id);
}
