package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.FilmController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.domain.mapper.AuthorMapper;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.FilmMapper;
import ru.lexa.books_reviews.domain.mapper.FilmReviewMapper;
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

	private AuthorMapper authorMapper;

	private BookMapper bookMapper;

	@Override
	public FilmDTO createFilm(FilmRequestDTO dto) {
		Book book = bookService.read(dto.getBookId());
		return filmMapper.filmToDto(filmService.create(filmMapper.dtoToFilm(dto, book.getAuthor(), book)));
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
		return filmMapper.filmToDto(filmService.update(filmMapper.dtoToFilm(dto, book.getAuthor(), book)));
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
	public AuthorDTO getAuthor(long id) {
		return authorMapper.authorToDto(filmService.read(id).getAuthor());
	}

	@Override
	public BookResponseDTO readBook(long id) {
		Book book = filmService.read(id).getBook();
		int reviewCount = book.getReview() == null ? 0 : book.getReview().size();
		double avgRating = bookService.averageRating(book.getId());
		return bookMapper.bookToDto(book, reviewCount, avgRating);
	}
}
