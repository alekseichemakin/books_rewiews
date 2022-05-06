package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.BookController;
import ru.lexa.books_reviews.controller.dto.*;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.ReviewMapper;
import ru.lexa.books_reviews.service.BookService;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class BookControllerImpl implements BookController {
	private final BookService bookService;

	private ReviewMapper reviewMapper;
	private BookMapper bookMapper;

	@Override
	public BookResponseDTO createBook(BookDTO dto) {
		return bookMapper.bookToDto(bookService.create(bookMapper.dtoToBook(dto)));
	}

	@Override
	public Collection<BookResponseDTO> readAll(String author, String description, String name, String reviewText) {
		BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText);
		return bookService.readAll(filter).stream().map(bookMapper::bookToDto).collect(Collectors.toList());
	}

	@Override
	public BookResponseDTO readBook(long id) {
		return bookMapper.bookToDto(bookService.read(id));
	}

	@Override
	public BookResponseDTO updateBook(BookResponseDTO dto) {
		return bookMapper.bookToDto(bookService.update(bookMapper.dtoToBook(dto)));
	}

	@Override
	public void deleteBook(long id) {
		bookService.delete(id);
	}

	@Override
	public Collection<ReviewResponseDTO> getReviews(long id) {
		return bookService.read(id).getReview().stream()
				.map(reviewMapper::reviewToDto)
				.collect(Collectors.toList());
	}

	@Override
	public double getAverage(long id) {
		return bookService.averageRating(id);
	}
}