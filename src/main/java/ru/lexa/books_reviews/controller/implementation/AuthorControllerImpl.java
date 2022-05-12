package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.AuthorController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.domain.mapper.AuthorMapper;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.FilmMapper;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.AuthorService;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class AuthorControllerImpl implements AuthorController {

	private AuthorService authorService;

	private AuthorMapper authorMapper;

	private BookMapper bookMapper;

	private FilmMapper filmMapper;

	@Override
	public AuthorDTO createAuthor(AuthorRequestDTO dto) {
		return authorMapper.authorToDto(authorService.create(authorMapper.dtoToAuthor(dto, null, null)));
	}

	@Override
	public Collection<AuthorDTO> readAll() {
		return authorService.readAll().stream().map(authorMapper::authorToDto).collect(Collectors.toList());
	}

	@Override
	public AuthorDTO readAuthor(long id) {
		return authorMapper.authorToDto(authorService.read(id));
	}

	@Override
	public AuthorDTO updateBook(AuthorDTO dto) {
		Collection<Book> books = authorService.read(dto.getId()).getBooks();
		Collection<Film> films = authorService.read(dto.getId()).getFilms();
		return authorMapper.authorToDto(authorService.update(authorMapper.dtoToAuthor(dto, books, films)));
	}

	@Override
	public void deleteAuthor(long id) {
		authorService.delete(id);
	}

	@Override
	public Collection<BookDTO> getBooks(long id) {
		return authorService.read(id).getBooks().stream()
				.map(bookMapper::bookToDto)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<FilmDTO> getFilms(long id) {
		return authorService.read(id).getFilms().stream()
				.map(filmMapper::filmToDto)
				.collect(Collectors.toList());
	}
}
