package controller;

import controller.dto.author.AuthorDTO;
import controller.dto.author.AuthorFilterDTO;
import controller.dto.author.AuthorRequestDTO;
import controller.dto.author.AuthorResponseDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Контроллер принимающий запросы для автора
 */
@RequestMapping("/api/authors")
public interface AuthorController {

	@ApiOperation(value = "Добавить нового автора.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    AuthorResponseDTO createAuthor(@RequestBody AuthorRequestDTO dto);

	@ApiOperation(value = "Поиск автров по параметрам.")
	@GetMapping
	Collection<AuthorResponseDTO> readAll(AuthorFilterDTO authorFilterDTO);

	@ApiOperation(value = "Получить автора.")
	@GetMapping("/{id}")
    AuthorResponseDTO readAuthor(@PathVariable long id);

	@ApiOperation(value = "Изменить автора.")
	@PutMapping
    AuthorResponseDTO updateAuthor(@RequestBody AuthorDTO dto);

	@ApiOperation(value = "Удалить автора.")
	@DeleteMapping("/{id}")
	void deleteAuthor(@PathVariable long id);

	@ApiOperation(value = "Получить средний рейтинг автора.")
	@GetMapping("/{id}/rating")
	Double getRating(@PathVariable long id);
}
