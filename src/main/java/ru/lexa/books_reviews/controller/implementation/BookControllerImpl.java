package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.BookController;
import ru.lexa.books_reviews.controller.dto.review.ReviewResponseDTO;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.controller.dto.book.BookRequestDTO;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.ReviewMapper;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.BookController}
 */
@AllArgsConstructor
@RestController
public class BookControllerImpl implements BookController {
	private final BookService bookService;

	private ReviewMapper reviewMapper;

	private BookMapper bookMapper;

	private AuthorService authorService;

	@Override
	public BookDTO createBook(BookRequestDTO dto) {
		Author author = authorService.read(dto.getAuthorId());
		System.out.println(author.getName());
		return bookMapper.bookToDto(bookService.create(bookMapper.dtoToBook(dto, author, null, null)));
	}

	@Override
	public Collection<BookDTO> readAll(String author, String description, String name, String reviewText) {
		BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText);
		return bookService.readAll(filter).stream().map(bookMapper::bookToDto).collect(Collectors.toList());
	}

	@Override
	public BookDTO readBook(long id) {
		return bookMapper.bookToDto(bookService.read(id));
	}

	@Override
	public BookDTO updateBook(BookDTO dto) {
		Author author = authorService.read(dto.getAuthorId());
		Collection<Review> reviews = bookService.read(dto.getId()).getReview();
		Collection<Film> films = bookService.read(dto.getId()).getFilms();
		return bookMapper.bookToDto(bookService.update(bookMapper.dtoToBook(dto, author, reviews, films)));
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