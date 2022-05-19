package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.BookController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.controller.dto.book.BookRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.domain.mapper.AuthorMapper;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.BookReviewMapper;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.BookController}
 */
@AllArgsConstructor
@RestController
public class BookControllerImpl implements BookController {
	private final BookService bookService;

	private BookReviewMapper reviewMapper;

	private BookMapper bookMapper;

	private AuthorService authorService;

	private AuthorMapper authorMapper;

	@Override
	//TODO read @Transactional
	public BookResponseDTO createBook(BookRequestDTO dto) {
		Collection<Author> authors = new ArrayList<>();
		for (Long id:dto.getAuthorIds()) {
			authors.add(authorService.read(id));
		}
		authors.forEach(author -> System.out.println(author.getId()));
		Book book = bookService.create(bookMapper.dtoToBook(dto, authors, null, null));
		return mapHelper(book);
	}

	@Override
	public Collection<BookResponseDTO> readAll(String author, String description, String name, String reviewText, Double maxRating) {
		BookFilterDTO filter = new BookFilterDTO(name, description, null, reviewText, maxRating);
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
		Set<Author> authors = new HashSet<>();
		for (Long id:dto.getAuthorIds()) {
			authors.add(authorService.read(id));
		}
		Collection<Review> reviews = bookService.read(dto.getId()).getReview();
		Collection<Film> films = bookService.read(dto.getId()).getFilms();
		Book book = bookMapper.dtoToBook(dto, authors, reviews, films);
		book = bookService.update(book);
		return mapHelper(book);
	}

	@Override
	public void deleteBook(long id) {
		bookService.delete(id);
	}

	@Override
	public Collection<BookReviewDTO> getReviews(long id) {
		return bookService.read(id).getReview().stream()
				.map(reviewMapper::reviewToDto)
				.collect(Collectors.toList());
	}

	@Override
	public double getAverage(long id) {
		return bookService.averageRating(id);
	}

	@Override
	public Collection<AuthorDTO> getAuthors(long id) {
		return bookService.read(id).getAuthors().stream()
				.map(authorMapper::authorToDto)
				.collect(Collectors.toList());
	}

	private BookResponseDTO mapHelper(Book book) {
		int reviewCount = book.getReview() == null ? 0 : book.getReview().size();
		double avgRating = bookService.averageRating(book.getId());
		List<Long> authorIds = new ArrayList<>();
		book.getAuthors().forEach(author -> authorIds.add(author.getId()));
		return bookMapper.bookToDto(book, reviewCount, avgRating, authorIds);
	}
}