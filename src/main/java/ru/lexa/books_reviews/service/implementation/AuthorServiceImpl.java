package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.AuthorNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.AuthorRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.List;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.AuthorService}
 */
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private AuthorRepository authorRepository;

	private BookService bookService;

	@Override
	public Author create(Author author) {
		try {
			return authorRepository.save(author);
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Override
	public Author read(long id) {
		return authorRepository.findById(id)
				.orElseThrow(() -> {
					throw new AuthorNotFoundException(id);
				});
	}

	@Override
	public Author update(Author author) {
		author.setBooks(read(author.getId()).getBooks());
		author.setFilms(read(author.getId()).getFilms());
		try {
			return authorRepository.save(author);
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Override
	public void delete(long id) {
		Author a = read(id);
		for (Book b : a.getBooks()) {
			if (b.getAuthors().size() == 1) {
				bookService.delete(b.getId());
			} else {
				b.getAuthors().remove(a);
				b.getFilms().forEach(film -> film.getAuthors().remove(a));
				authorRepository.save(a);
			}
		}
		authorRepository.delete(a);
	}

	@Override
	public List<Author> readAll() {
		return authorRepository.findAll();
	}
}
