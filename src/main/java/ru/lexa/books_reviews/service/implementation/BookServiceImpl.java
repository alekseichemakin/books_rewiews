package ru.lexa.books_reviews.service.implementation;


import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.exception.ReviewNotFoundException;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.repository.specification.BookSpecification;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.BookService}
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	private FilmService filmService;

	@Override
	public Book create(Book book) {
		try {
			return bookRepository.save(book);
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Override
	public Book read(long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> {throw new BookNotFoundException(id);});
	}

	@Override
	public void delete(long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> {throw new BookNotFoundException(id);});
		bookRepository.delete(book);
	}

	@Override
	public Book update(Book book) {
		book.setReview(bookRepository.findById(book.getId())
				.orElseThrow(() -> {throw new BookNotFoundException(book.getId());})
				.getReview());
		Collection<Film> films = book.getFilms();
		films.forEach(film -> film.setAuthor(book.getAuthor()));
		films = films.stream().map(filmService::update).collect(Collectors.toList());
		book.setFilms(films);
		try {
			return bookRepository.save(book);
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Override
	public double averageRating(long id) {
		Book book = read(id);
		return book.getReview() == null || book.getReview().size() == 0 ?
				0 : book.getReview().stream().mapToDouble(Review::getRating).sum() / book.getReview().size();
	}

	@Override
	public List<Book> readAll(BookFilterDTO filter) {
		Specification<Book> spec = Specification
				.where(BookSpecification.likeName(filter.getName()))
				.and(BookSpecification.likeAuthor(filter.getAuthor()))
				.and(BookSpecification.likeDescription(filter.getDescription()))
				.and(BookSpecification.likeReviewText(filter.getReviewText()))
				.and(BookSpecification.lesThenRating(filter.getLessThenRating()));
		return bookRepository.findAll(spec);
	}
}
