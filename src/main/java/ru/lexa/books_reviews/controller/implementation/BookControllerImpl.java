package ru.lexa.books_reviews.controller.implementation;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@Override
	public BookDTO createBook(@Valid @RequestBody BookDTO dto) {
		return bookMappingService.mapToBookDto(bookService.create(bookMappingService.mapToBookEntity(dto)));
	}

	@ApiOperation(value = "Получить книги.")
	@GetMapping
	@Override
	public Collection<BookDTO> readAll(@RequestParam(required = false) String author,
	                                   @RequestParam(required = false) String description,
	                                   @RequestParam(required = false) String name,
	                                   @RequestParam(required = false) String reviewText) {
		BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText);
		return bookService.readAll(filter).stream().map(book -> bookMappingService.mapToBookDto(book)).collect(Collectors.toList());
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
				.map(review -> reviewMappingService.mapToReviewDto(review))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Получить среднюю оценку.")
	@GetMapping("/{id}/averageRating")
	public double getAverage(@PathVariable long id) {
		return bookService.averageRating(id);
	}
}