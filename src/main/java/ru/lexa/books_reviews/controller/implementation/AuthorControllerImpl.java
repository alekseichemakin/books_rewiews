package ru.lexa.books_reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.AuthorController;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorFilterDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.domain.mapper.AuthorMapper;
import ru.lexa.books_reviews.domain.mapper.BookMapper;
import ru.lexa.books_reviews.domain.mapper.FilmMapper;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.ArrayList;
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

	private BookMapper bookMapper;

	private FilmMapper filmMapper;

	private BookService bookService;

	@Override
	public AuthorDTO createAuthor(AuthorRequestDTO dto) {
		return authorMapper.authorToDto(authorService.create(authorMapper.dtoToAuthor(dto)));
	}

	@Override
	public Collection<AuthorDTO> readAll(Integer page, Integer pageSize, Double maxRating, String name, String book, String film) {
		AuthorFilterDTO filter = new AuthorFilterDTO(name, book, film, maxRating, page, pageSize);
		return authorService.readAll(filter).stream().map(authorMapper::authorToDto).collect(Collectors.toList());
	}

	@Override
	public AuthorDTO readAuthor(long id) {
		return authorMapper.authorToDto(authorService.read(id));
	}

	@Override
	public AuthorDTO updateAuthor(AuthorDTO dto) {
		return authorMapper.authorToDto(authorService.update(authorMapper.dtoToAuthor(dto)));
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
				.map(this::mapHelper)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<FilmDTO> readFilms(long id) {
		return authorService.read(id).getFilms().stream()
				.map(filmMapper::filmToDto)
				.collect(Collectors.toList());
	}

	private BookResponseDTO mapHelper(Book book) {
		int reviewCount = book.getReview() == null ? 0 : book.getReview().size();
		double avgRating = bookService.averageRating(book.getId());
		List<Long> authorIds = new ArrayList<>();
		book.getAuthors().forEach(author -> authorIds.add(author.getId()));
		return bookMapper.bookToDto(book, reviewCount, avgRating, authorIds);
	}
}
