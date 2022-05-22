package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.FilmController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.domain.mapper.*;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;

import java.util.Collection;
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

	private MapHelper mapHelper;

	@Override
	public FilmDTO createFilm(FilmRequestDTO dto) {
		Book book = bookService.read(dto.getBookId());
		return filmMapper.filmToDto(filmService.create(filmMapper.dtoToFilm(dto, book.getAuthors(), book)));
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
		Book book = bookService.read(dto.getBookId());
		return filmMapper.filmToDto(filmService.update(filmMapper.dtoToFilm(dto, book.getAuthors(), book)));
	}

	@Override
	public void deleteFilm(long id) {
		filmService.delete(id);
	}

	@Override
	public Collection<FilmReviewDTO> getReviews(long id) {
		return filmService.read(id).getReview().stream()
				.map(reviewMapper::reviewToDto)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<AuthorDTO> getAuthors(long id) {
		return filmService.read(id).getAuthors().stream()
				.map(mapHelper::authorMapHelper)
				.collect(Collectors.toList());
	}

	@Override
	public BookResponseDTO readBook(long id) {
		Book book = filmService.read(id).getBook();
		return mapHelper.bookMapHelper(book);
	}
}
