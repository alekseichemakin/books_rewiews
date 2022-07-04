package ru.lexa.books_reviews.controller.implementation;

import controller.FilmController;
import controller.dto.book.BookResponseDTO;
import controller.dto.film.FilmDTO;
import controller.dto.film.FilmRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.mapper.BookMapper;
import ru.lexa.books_reviews.controller.mapper.FilmMapper;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.entity.Author;
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

	private BookMapper bookMapper;

	@Override
	public FilmDTO createFilm(FilmRequestDTO dto) {
		return filmMapper.filmToDto(filmService.create(filmMapper.dtoToFilm(dto)));
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
		return filmMapper.filmToDto(filmService.update(filmMapper.dtoToFilm(dto)));
	}

	@Override
	public void deleteFilm(long id) {
		filmService.delete(id);
	}

	@Override
	public BookResponseDTO readBook(long id) {
		BookDomain bookDomain = bookService.read(filmService.read(id).getBook().getId());
		BookResponseDTO bookResponseDTO = bookMapper.bookToDto(bookDomain);
		bookResponseDTO.setAuthorIds(bookDomain.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
		return bookResponseDTO;
	}
}
