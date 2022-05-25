package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.FilmController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.mapper.*;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.repository.mapper.ReviewDomainMapper;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.FilmController}
 */
@AllArgsConstructor
@RestController
public class FilmControllerImpl implements FilmController {

	private FilmService filmService;

	private FilmMapper filmMapper;

	private BookService bookService;

	private FilmReviewMapper reviewMapper;

	private BookDomainMapper bookDomainMapper;

	private AuthorDomainMapper authorDomainMapper;

	private AuthorMapper authorMapper;

	private BookMapper bookMapper;

	private ReviewDomainMapper reviewDomainMapper;

	@Override
	public FilmDTO createFilm(FilmRequestDTO dto) {
		Book book = bookDomainMapper.domainToBook(bookService.read(dto.getBookId()));
		FilmDomain filmDomain = filmMapper.dtoToFilm(dto);
		filmDomain.setBook(book);
		filmDomain.setAuthors(book.getAuthors());
		return filmMapper.filmToDto(filmService.create(filmDomain));
	}

	@Override
	public Collection<FilmDTO> readAll() {
		return filmService.readAll().stream()
				.map(filmMapper::filmToDto)
				.collect(Collectors.toList());
	}

	@Override
	public FilmDTO readFilm(long id) {
		return filmMapper.filmToDto(filmService.read(id));
	}

	@Override
	public FilmDTO updateFilm(FilmDTO dto) {
		Book book = bookDomainMapper.domainToBook(bookService.read(dto.getBookId()));
		FilmDomain filmDomain = filmMapper.dtoToFilm(dto);
		filmDomain.setBook(book);
		filmDomain.setAuthors(book.getAuthors());
		return filmMapper.filmToDto(filmService.update(filmDomain));
	}

	@Override
	public void deleteFilm(long id) {
		filmService.delete(id);
	}

	@Override
	public Collection<FilmReviewDTO> getReviews(long id) {
		Collection<FilmReviewDTO> reviews = filmService.read(id).getReviews().stream()
				.map(review -> reviewMapper.reviewToDto(reviewDomainMapper.reviewToDomain(review)))
				.collect(Collectors.toList());
		reviews.forEach(r -> r.setFilmId(id));
		return reviews;
	}

	@Override
	public Collection<AuthorDTO> getAuthors(long id) {
		List<AuthorDomain> authorDomains = filmService.read(id).getAuthors().stream().map(authorDomainMapper::authorToDomain).collect(Collectors.toList());
		authorDomains.forEach(domain -> domain.setBookIds(domain.getBooks().stream().map(Book::getId).collect(Collectors.toList())));
		authorDomains.forEach(domain -> domain.setFilmIds(domain.getFilms().stream().map(Film::getId).collect(Collectors.toList())));
		return authorDomains.stream().map(authorMapper::authorToDto).collect(Collectors.toList());
	}

	@Override
	public BookResponseDTO readBook(long id) {
		return bookMapper.bookToDto(bookService.read(filmService.read(id).getBookId()));
	}
}
