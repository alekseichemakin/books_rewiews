package controller;

import controller.dto.author.AuthorDTO;
import controller.dto.book.BookResponseDTO;
import controller.dto.film.FilmDTO;
import controller.dto.film.FilmRequestDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Контроллер принимающий запросы для фильмов
 */
@RequestMapping("/api/films")
@Validated
public interface FilmController {
	@ApiOperation(value = "Добавить новый фильм.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    FilmDTO createFilm(@RequestBody FilmRequestDTO dto);

	@ApiOperation(value = "Получить все фильмы.")
	@GetMapping
	Collection<FilmDTO> readAll();

	@ApiOperation(value = "Получить фильм.")
	@GetMapping("/{id}")
    FilmDTO readFilm(@PathVariable long id);

	@ApiOperation(value = "Изменить фильм.")
	@PutMapping
    FilmDTO updateFilm(@RequestBody FilmDTO dto);

	@ApiOperation(value = "Удалить фильм.")
	@DeleteMapping("/{id}")
	void deleteFilm(@PathVariable long id);

	@ApiOperation(value = "Получить автора фильма.")
	@GetMapping("/{id}/authors")
	Collection<AuthorDTO> getAuthors(@PathVariable long id);

	@ApiOperation(value = "Получить экранизированную книгу.")
	@GetMapping("/{id}/book")
    BookResponseDTO readBook(@PathVariable long id);
}
