package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;

import java.util.Collection;

@RequestMapping("/api/authors")
public interface AuthorController {
	@ApiOperation(value = "Добавить нового автора.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	AuthorDTO createAuthor(@RequestBody AuthorRequestDTO dto);

	@ApiOperation(value = "Получить всех авторов.")
	@GetMapping
	Collection<AuthorDTO> readAll();

	@ApiOperation(value = "Получить автора.")
	@GetMapping("/{id}")
	AuthorDTO readAuthor(@PathVariable long id);

	@ApiOperation(value = "Изменить автора.")
	@PutMapping
	AuthorDTO updateBook(@RequestBody AuthorDTO dto);

	@ApiOperation(value = "Удалить автора.")
	@DeleteMapping("/{id}")
	void deleteAuthor(@PathVariable long id);

	@ApiOperation(value = "Получить книги автора.")
	@GetMapping("/{id}/books")
	Collection<BookDTO> getBooks(@PathVariable long id);

	@ApiOperation(value = "Получить фильмы автора.")
	@GetMapping("/{id}/films")
	Collection<FilmDTO> getFilms(@PathVariable long id);
}
