package ru.lexa.books_reviews.controller.implementation;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.BookController;
import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.controller.dto.BookFilterDTO;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.ReviewMapper;
import ru.lexa.books_reviews.service.BookService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookControllerImpl implements BookController {
	private final BookService bookService;

	private ReviewMapper reviewMapper;
	private BookMapper bookMapper;

	@ApiOperation(value = "Добавить новую книгу.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public BookDTO createBook(@RequestBody BookDTO dto) {
		return bookMapper.bookToDto(bookService.create(bookMapper.dtoToBook(dto)));
	}

	@ApiOperation(value = "Получить все книги.")
	@GetMapping
	@Override
	public Collection<BookDTO> readAll(@RequestParam(required = false) String author,
	                                   @RequestParam(required = false) String description,
	                                   @RequestParam(required = false) String name,
	                                   @RequestParam(required = false) String reviewText) {
		BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText);
		return bookService.readAll(filter).stream().map(bookMapper::bookToDto).collect(Collectors.toList());
	}

	@ApiOperation(value = "Получить книгу.")
	@GetMapping("/{id}")
	@Override
	public BookDTO readBook(@PathVariable long id) {
		return bookMapper.bookToDto(bookService.read(id));
	}

	@ApiOperation(value = "Изменить книгу.")
	@PutMapping("/{id}")
	@Override
	public BookDTO updateBook(@RequestBody BookDTO dto, @PathVariable long id) {
		dto.setId(id);
		return bookMapper.bookToDto(bookService.update(bookMapper.dtoToBook(dto)));
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
				.map(reviewMapper::reviewToDto)
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Получить среднюю оценку.")
	@GetMapping("/{id}/averageRating")
	public double getAverage(@PathVariable long id) {
		return bookService.averageRating(id);
	}
}