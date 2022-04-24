package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.model.Review;
import ru.lexa.books_reviews.service.BookService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	private BookService bookService;

	@ApiOperation(value = "добавить новую книгу")
	@PostMapping
	public Book addNewBook(@Valid @RequestBody Book book) {
		return bookService.create(book);
	}

	@ApiOperation(value = "получить книги")
	@GetMapping
	public Collection<Book> getAll(@RequestParam(required = false) String author,
								   @RequestParam(required = false) String description,
								   @RequestParam(required = false) String name,
								   @RequestParam(required = false) String reviewText) {
		HashMap<String, String> params = new HashMap<>();
		params.put("author", author);
		params.put("description", description);
		params.put("name", name);
		params.put("reviewText", reviewText);
		return bookService.readFilter(params);
	}

	@ApiOperation(value = "получить книгу")
	@GetMapping("/{id}")
	public Book getBook(@PathVariable Long id) {
		return bookService.read(id);
	}

	@ApiOperation(value = "изменить книгу")
	@PutMapping
	public Book updateAuthor(@RequestBody Book book) {
		return bookService.update(book);
	}

	@ApiOperation(value = "удалить книгу")
	@DeleteMapping("/{id}")
	public String deleteSurvey(@PathVariable Long id) {
		bookService.delete(id);
		return String.format("книга с идентификором %d была удалена", id);
	}

	@ApiOperation(value = "получить отзывы к книге")
	@GetMapping("/{id}/reviews")
	public Collection<Review> getReviews(@PathVariable Long id) {
		return bookService.read(id).getReview();
	}

	@ApiOperation(value = "получить среднюю оценку")
	@GetMapping("/{id}/averageRating")
	public double getAverage(@PathVariable Long id) {
		return bookService.averageRating(id);
	}
}