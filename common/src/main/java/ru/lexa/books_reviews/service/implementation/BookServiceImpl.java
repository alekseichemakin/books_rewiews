package ru.lexa.books_reviews.service.implementation;


import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.repository.specification.BookSpecification;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;

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

	private FilmDomainMapper filmDomainMapper;

	private RestTemplate restTemplate;

	private FilmService filmService;

	@Transactional
	@Override
	public BookDomain create(BookDomain book) {
		BookDomain domain;
		try {
			domain = bookDomainMapper.bookToDomain(bookRepository.save(bookDomainMapper.domainToBook(book)));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
		return domain;
	}

	@Override
	public BookDomain read(long id) {
		BookDomain domain = bookDomainMapper.bookToDomain(bookRepository.findById(id)
				.orElseThrow(() -> {
					throw new BookNotFoundException(id);
				}));
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

	@Transactional
	@Override
	public BookDomain update(BookDomain book) {
		BookDomain finalBook = book;
		Book updatableBook = bookRepository.findById(book.getId())
				.orElseThrow(() -> {
					throw new BookNotFoundException(finalBook.getId());
				});
		updatableBook.setAuthors(book.getAuthors());
		updatableBook.setDescription(book.getDescription());
		updatableBook.setName(book.getName());
		Collection<Film> films = updatableBook.getFilms();
		films.forEach(film -> film.setAuthors(finalBook.getAuthors()));
		films.forEach(film -> filmService.update(filmDomainMapper.filmToDomain(film)));
		try {
			book = bookDomainMapper.bookToDomain(bookRepository.save(updatableBook));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
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
		Double rating = restTemplate.getForObject("http://localhost:8081/api/books/reviews/averageRating/{bookId}",
				Double.class,
				id);
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
		return bookDomains;
	}
}
