package ru.lexa.books_reviews.controller.implementation;

import controller.FilmController;
import controller.dto.author.AuthorDTO;
import controller.dto.author.AuthorResponseDTO;
import controller.dto.book.BookResponseDTO;
import controller.dto.film.FilmDTO;
import controller.dto.film.FilmRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.mapper.AuthorMapper;
import ru.lexa.books_reviews.controller.mapper.BookMapper;
import ru.lexa.books_reviews.controller.mapper.FilmMapper;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
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

	private BookDomainMapper bookDomainMapper;

	private AuthorDomainMapper authorDomainMapper;

	private AuthorMapper authorMapper;

	private BookMapper bookMapper;

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
	public BookResponseDTO readBook(long id) {
		BookDomain bookDomain = bookService.read(filmService.read(id).getBook().getId());
		BookResponseDTO bookResponseDTO = bookMapper.bookToDto(bookDomain);
		bookResponseDTO.setAuthorIds(bookDomain.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
		return bookResponseDTO;
	}

	@Override
	public Collection<AuthorDTO> getAuthors(long id) {
		List<AuthorDomain> authorDomains = filmService.read(id).getAuthors().stream().map(authorDomainMapper::authorToDomain).collect(Collectors.toList());
		return authorDomains.stream().map(this::setBookAuthorIds).collect(Collectors.toList());
	}

	private AuthorResponseDTO setBookAuthorIds(AuthorDomain domain) {
		AuthorResponseDTO dto = authorMapper.authorToDto(domain);
		dto.setBookIds(domain.getBooks().stream().map(Book::getId).collect(Collectors.toList()));
		dto.setFilmIds(domain.getFilms().stream().map(Film::getId).collect(Collectors.toList()));
		return dto;
	}
}
