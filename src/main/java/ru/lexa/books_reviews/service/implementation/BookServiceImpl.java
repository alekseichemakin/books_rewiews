package ru.lexa.books_reviews.service.implementation;


import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.repository.specification.BookSpecification;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.BookService}
 */
@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	private BookDomainMapper bookDomainMapper;

	private ReviewService reviewService;

	private FilmDomainMapper filmDomainMapper;

	@Transactional
	@Override
	public BookDomain create(BookDomain book) {
		BookDomain domain;
		try {
			domain = bookDomainMapper.bookToDomain(bookRepository.save(bookDomainMapper.domainToBook(book)));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
		domain.setAuthorIds(domain.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
		return domain;
	}

	@Override
	public BookDomain read(long id) {
		BookDomain domain = bookDomainMapper.bookToDomain(bookRepository.findById(id)
				.orElseThrow(() -> {
					throw new BookNotFoundException(id);
				}));
		domain.setAuthorIds(domain.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
		domain.setAverageRating(averageRating(id));
		domain.setReviewCount(domain.getReviews().size());
		return domain;
	}

	@Transactional
	@Override
	public void delete(long id) {
		bookRepository.delete(bookRepository.findById(id)
				.orElseThrow(() -> {
					throw new BookNotFoundException(id);
				}));
	}

	@Override
	public BookDomain update(BookDomain book) {
		book.setFilms(read(book.getId()).getFilms());
		book.setReviews(read(book.getId()).getReviews());
		Collection<Film> films = book.getFilms();
		BookDomain finalBook = book;
		films.forEach(film -> film.setAuthors(finalBook.getAuthors()));
		films.forEach(film -> filmDomainMapper.filmToDomain(film));

		try {
			book = bookDomainMapper.bookToDomain(bookRepository.save(bookDomainMapper.domainToBook(book)));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
		book.setAuthorIds(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
		book.setAverageRating(averageRating(book.getId()));
		book.setReviewCount(book.getReviews().size());
		return book;
	}

	@Override
	public double averageRating(long id) {
		bookRepository.findById(id)
				.orElseThrow(() -> {
					throw new BookNotFoundException(id);
				});
		Double rating = reviewService.getBookAverageRating(id);
		return rating == null ? 0 : rating;
	}

	@Override
	public List<BookDomain> readAll(BookFilterDTO filter) {
		Specification<Book> spec = Specification
				.where(BookSpecification.likeName(filter.getName()))
				.and(BookSpecification.likeAuthor(filter.getAuthor()))
				.and(BookSpecification.likeDescription(filter.getDescription()))
				.and(BookSpecification.likeReviewText(filter.getReviewText()))
				.and(BookSpecification.lesThenRating(filter.getLessThenRating()));
		List<Book> books;
		if (filter.getPage() != null && filter.getPageSize() != null) {
			Pageable page = PageRequest.of(filter.getPage(), filter.getPageSize());
			books = bookRepository.findAll(spec, page).toList();
		} else {
			books = bookRepository.findAll(spec);
		}
		List<BookDomain> bookDomains = books.stream().map(bookDomainMapper::bookToDomain).collect(Collectors.toList());
		bookDomains.forEach(domain -> domain.setReviewCount(domain.getReviews().size()));
		bookDomains.forEach(domain -> domain.setAverageRating(averageRating(domain.getId())));
		bookDomains.forEach(domain -> domain.setAuthorIds(domain.getAuthors().stream().map(Author::getId).collect(Collectors.toList())));
		return bookDomains;
	}
}
