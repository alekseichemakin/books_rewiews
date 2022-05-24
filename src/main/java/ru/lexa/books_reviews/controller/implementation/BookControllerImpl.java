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
import ru.lexa.books_reviews.controller.mapper.BookMapper;
import ru.lexa.books_reviews.controller.mapper.BookReviewMapper;
import ru.lexa.books_reviews.controller.mapper.MapHelper;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

	private MapHelper mapHelper;

	private AuthorDomainMapper authorDomainMapper;

	@Override
	public BookResponseDTO createBook(BookRequestDTO dto) {
		BookDomain domain = bookMapper.dtoToBook(dto);
		List<Author> authors = new ArrayList<>();
		for (long id: domain.getAuthorIds()) {
			authors.add(authorDomainMapper.domainToAuthor(authorService.read(id)));
		}
		domain.setAuthors(authors);
		return bookMapper.bookToDto(bookService.create(domain));
	}

	@Override
	public Collection<BookResponseDTO> readAll(Integer page, Integer pageSize,
	                                           String author, String description,
	                                           String name, String reviewText,
	                                           Double maxRating) {
		BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText, maxRating, page, pageSize);
		return bookService.readAll(filter).stream()
				.map(bookMapper::bookToDto)
				.collect(Collectors.toList());
	}

	@Override
	public BookResponseDTO readBook(long id) {
		return bookMapper.bookToDto(bookService.read(id));
	}

	@Override
	public BookResponseDTO updateBook(BookDTO dto) {
		BookDomain domain = bookMapper.dtoToBook(dto);
		List<Author> authors = new ArrayList<>();
		for (long id: domain.getAuthorIds()) {
			authors.add(authorDomainMapper.domainToAuthor(authorService.read(id)));
		}
		domain.setAuthors(authors);
		return bookMapper.bookToDto(bookService.update(domain));
	}

	@Override
	public void deleteBook(long id) {
		bookService.delete(id);
	}

	@Override
	public Collection<BookReviewDTO> getReviews(long id) {
		return bookService.read(id).getReviews().stream()
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
				.map(mapHelper::authorMapHelper)
				.collect(Collectors.toList());
	}
}