package controller;

import controller.dto.book.BookDTO;
import controller.dto.book.BookFilterDTO;
import controller.dto.book.BookResponseDTO;
import controller.dto.book.BookReviewUnitedDTO;
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
public interface  BookController {

	@ApiOperation(value = "Добавить новую книгу.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
    BookResponseDTO createBook(@RequestBody @Valid BookReviewUnitedDTO bookReviewUnitedDTO);

	@ApiOperation(value = "Поиск книг по параметрам.")
	@GetMapping
	Collection<BookResponseDTO> readAll(BookFilterDTO filterDTO);

	@ApiOperation(value = "Получить книгу.")
	@GetMapping("/{id}")
    BookResponseDTO readBook(@PathVariable("id") long id);

	@ApiOperation(value = "Изменить книгу.")
	@PutMapping
    BookResponseDTO updateBook(@RequestBody BookDTO dto);

	@ApiOperation(value = "Удалить книгу.")
	@DeleteMapping("/{id}")
	void deleteBook(@PathVariable("id") long id);

	@ApiOperation(value = "Получить среднюю оценку.")
	@GetMapping("/{id}/averageRating")
	double getAverage(@PathVariable("id") long id);

	@ApiOperation(value = "Отчистить кэш книги.")
	@GetMapping("/{id}/clearCache")
	void clearCache(@PathVariable("id") long id);
}
