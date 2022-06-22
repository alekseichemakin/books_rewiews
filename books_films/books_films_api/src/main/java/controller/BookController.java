package controller;

import controller.dto.author.AuthorDTO;
import controller.dto.book.BookDTO;
import controller.dto.book.BookRequestDTO;
import controller.dto.book.BookResponseDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@ApiOperation(value = "Поиск книг по параметрам.")
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

	@ApiOperation(value = "Получить среднюю оценку.")
	@GetMapping("/{id}/averageRating")
	double getAverage(@PathVariable long id);

	@ApiOperation(value = "Получить авторов книги.")
	@GetMapping("/{id}/authors")
	Collection<AuthorDTO> getAuthors(@PathVariable long id);
}
