package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.repository.AuthorRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.service.AuthorService;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private AuthorRepository authorRepository;

	@Override
	public Author create(Author author) {
		try {
			return authorRepository.save(author);
		} catch (DataIntegrityViolationException e) {
			throw new InputErrorException("Неверное имя");
		}
	}

	@Override
	public Author read(long id) {
		return authorRepository.findById(id)
				.orElseThrow(() -> new InputErrorException("Нет автора с данным id"));
	}

	@Override
	public Author update(Author author) {
		author.setBooks(read(author.getId()).getBooks());
		author.setFilms(read(author.getId()).getFilms());
		try {
			return authorRepository.save(author);
		} catch (DataIntegrityViolationException e) {
			throw new InputErrorException("Неверное имя");
		}
	}

	@Override
	public void delete(long id) {
		authorRepository.delete(read(id));
	}

	@Override
	public List<Author> readAll() {
		return authorRepository.findAll();
	}
}
