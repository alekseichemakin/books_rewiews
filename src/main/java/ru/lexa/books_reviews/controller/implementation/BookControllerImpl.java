package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.BookController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.review.ReviewResponseDTO;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.controller.dto.book.BookRequestDTO;
import ru.lexa.books_reviews.domain.mapper.AuthorMapper;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.ReviewMapper;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
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

	private AuthorMapper authorMapper;

	@Override
	public BookResponseDTO createBook(BookRequestDTO dto) {
		Author author = authorService.read(dto.getAuthorId());
		Book book = bookService.create(bookMapper.dtoToBook(dto, author, null, null));
		return mapHelper(book);
	}

	@Override
	public Collection<BookResponseDTO> readAll(String author, String description, String name, String reviewText) {
		BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText);
		return bookService.readAll(filter).stream()
				.map(this::mapHelper)
				.collect(Collectors.toList());
	}

	@Override
	public BookResponseDTO readBook(long id) {
		return mapHelper(bookService.read(id));
	}

	@Override
	public BookResponseDTO updateBook(BookDTO dto) {
		Author author = authorService.read(dto.getAuthorId());
		Collection<Review> reviews = bookService.read(dto.getId()).getReview();
		Collection<Film> films = bookService.read(dto.getId()).getFilms();
		Book book = bookMapper.dtoToBook(dto, author, reviews, films);
		book = bookService.update(book);
		return mapHelper(book);
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

	@Override
	public AuthorDTO getAuthor(long id) {
		return authorMapper.authorToDto(bookService.read(id).getAuthor());
	}

	private BookResponseDTO mapHelper(Book book) {
		int reviewCount = book.getReview() == null ? 0 : book.getReview().size();
		double averageRating = bookService.averageRating(book.getId());
		return bookMapper.bookToDto(book, reviewCount, averageRating);
	}
}