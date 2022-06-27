package ru.lexa.books_reviews.controller.implementation;

import controller.AuthorController;
import controller.dto.author.AuthorDTO;
import controller.dto.author.AuthorFilterDTO;
import controller.dto.author.AuthorRequestDTO;
import controller.dto.author.AuthorResponseDTO;
import controller.dto.book.BookResponseDTO;
import controller.dto.film.FilmDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.mapper.AuthorMapper;
import ru.lexa.books_reviews.controller.mapper.BookMapper;
import ru.lexa.books_reviews.controller.mapper.FilmMapper;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.service.AuthorService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.AuthorController}
 */
@AllArgsConstructor
@RestController
public class AuthorControllerImpl implements AuthorController {

	private AuthorService authorService;

	private AuthorMapper authorMapper;

	private FilmMapper filmMapper;

	private BookDomainMapper bookDomainMapper;

	private BookMapper bookMapper;

	private FilmDomainMapper filmDomainMapper;


	@Override
	public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
		return authorMapper.authorToDto(authorService.create(authorMapper.dtoToAuthor(dto)));
	}

	@Override
	public Collection<AuthorResponseDTO> readAll(AuthorFilterDTO authorFilterDTO) {
		return authorService.readAll(authorFilterDTO).stream().map(authorMapper::authorToDto).collect(Collectors.toList());
	}

	@Override
	public AuthorResponseDTO readAuthor(long id) {
		return authorMapper.authorToDto(authorService.read(id));
	}

	@Override
	public AuthorResponseDTO updateAuthor(AuthorDTO dto) {
		return authorMapper.authorToDto(authorService.update(authorMapper.dtoToAuthor(dto)));
	}

	@Override
	public void deleteAuthor(long id) {
		authorService.delete(id);
	}

	@Override
	public Collection<BookResponseDTO> readBooks(long id) {
		List<BookDomain> bookDomainList = authorService.read(id).getBooks().stream()
				.map(bookDomainMapper::bookToDomain).toList();
		return bookDomainList.stream().map(b -> {
					BookResponseDTO dto = bookMapper.bookToDto(b);
					dto.setAuthorIds(b.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public Collection<FilmDTO> readFilms(long id) {
		return authorService.read(id).getFilms().stream()
				.map(film -> filmMapper.filmToDto(filmDomainMapper.filmToDomain(film)))
				.collect(Collectors.toList());
	}

	@Override
	public Double getRating(long id) {
		return authorService.getAverageRating(id);
	}
}
