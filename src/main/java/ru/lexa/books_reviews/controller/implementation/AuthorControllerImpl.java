package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.AuthorController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorFilterDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorResponseDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.domain.mapper.AuthorMapper;
import ru.lexa.books_reviews.domain.mapper.FilmMapper;
import ru.lexa.books_reviews.domain.mapper.MapHelper;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.service.AuthorService;

import java.util.Collection;
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

	private MapHelper mapHelper;

	@Override
	public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
		Author author = authorService.create(authorMapper.dtoToAuthor(dto));
		return mapHelper.authorMapHelper(author);
	}

	@Override
	public Collection<AuthorResponseDTO> readAll(Integer page, Integer pageSize, Double maxRating, String name, String book, String film) {
		AuthorFilterDTO filter = new AuthorFilterDTO(name, book, film, maxRating, page, pageSize);
		return authorService.readAll(filter).stream().map(mapHelper::authorMapHelper).collect(Collectors.toList());
	}

	@Override
	public AuthorResponseDTO readAuthor(long id) {
		return mapHelper.authorMapHelper(authorService.read(id));
	}

	@Override
	public AuthorResponseDTO updateAuthor(AuthorDTO dto) {
		return mapHelper.authorMapHelper(authorService.update(authorMapper.dtoToAuthor(dto)));
	}

	@Override
	public void deleteAuthor(long id) {
		authorService.delete(id);
	}

	@Override
	//TODO ret Добиться появления Exception
	public Collection<BookResponseDTO> readBooks(long id) {
//TODO		authorService.read(id).getBooks().toString(); stackOverFlow
		return authorService.read(id).getBooks().stream()
				.map(mapHelper::bookMapHelper)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<FilmDTO> readFilms(long id) {
		return authorService.read(id).getFilms().stream()
				.map(filmMapper::filmToDto)
				.collect(Collectors.toList());
	}
}
