package ru.lexa.books_reviews.controller.implementation;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.BookController;
import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.controller.dto.BookFilterDTO;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.service.BookMappingService;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewMappingService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookControllerImpl implements BookController {
	private final BookService bookService;

	private final BookMappingService bookMappingService;

	private final ReviewMappingService reviewMappingService;


	@ApiOperation(value = "Добавить новую книгу.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public BookDTO createBook(@Valid @RequestBody BookDTO dto) {
		return bookMappingService.mapToBookDto(bookService.create(bookMappingService.mapToBookEntity(dto)));
	}

	@ApiOperation(value = "Получить все книги.")
	@GetMapping
	@Override
	public Collection<BookDTO> readAll(@RequestParam(required = false) String author,
	                                   @RequestParam(required = false) String description,
	                                   @RequestParam(required = false) String name,
	                                   @RequestParam(required = false) String reviewText) {
		BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText);
		return bookService.readAll(filter).stream().map(bookMappingService::mapToBookDto).collect(Collectors.toList());
	}

	@ApiOperation(value = "Получить книгу.")
	@GetMapping("/{id}")
	@Override
	public BookDTO readBook(@PathVariable long id) {
		return bookMappingService.mapToBookDto(bookService.read(id));
	}

	@ApiOperation(value = "Изменить книгу.")
	@PutMapping("/{id}")
	@Override
	public BookDTO updateBook(@RequestBody BookDTO dto, @PathVariable long id) {
		dto.setId(id);
		return bookMappingService.mapToBookDto(bookService.update(bookMappingService.mapToBookEntity(dto)));
	}

	@ApiOperation(value = "Удалить книгу.")
	@DeleteMapping("/{id}")
	@Override
	public void deleteBook(@PathVariable long id) {
		bookService.delete(id);
	}

	@ApiOperation(value = "Получить отзывы к книге.")
	@GetMapping("/{id}/reviews")
	@Override
	public Collection<ReviewDTO> getReviews(@PathVariable long id) {
		return bookService.read(id).getReview().stream()
				.map(reviewMappingService::mapToReviewDto)
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Получить среднюю оценку.")
	@GetMapping("/{id}/averageRating")
	public double getAverage(@PathVariable long id) {
		return bookService.averageRating(id);
	}
}