package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.controller.dto.author.AuthorFilterDTO;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.exception.AuthorNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.AuthorRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.repository.specification.AuthorSpecification;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.AuthorService}
 */
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private AuthorRepository authorRepository;

	private BookService bookService;

	private AuthorDomainMapper authorDomainMapper;

	@Override
	public AuthorDomain create(AuthorDomain author) {
		try {
			return authorDomainMapper
					.authorToDomain(authorRepository.save(authorDomainMapper
							.domainToAuthor(author)));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Override
	public AuthorDomain read(long id) {
		Author author =  authorRepository.findById(id)
				.orElseThrow(() -> {throw new AuthorNotFoundException(id);});
		AuthorDomain authorDomain = authorDomainMapper.authorToDomain(author);
		authorDomain.setBookIds(author.getBooks().stream().map(Book::getId).collect(Collectors.toList()));
		authorDomain.setFilmIds(author.getFilms().stream().map(Film::getId).collect(Collectors.toList()));
		return authorDomain;
	}

	@Override
	public AuthorDomain update(AuthorDomain author) {
		author.setBooks(read(author.getId()).getBooks());
		author.setFilms(read(author.getId()).getFilms());
		try {
			return authorDomainMapper
					.authorToDomain(authorRepository.save(authorDomainMapper
							.domainToAuthor(author)));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Override
	public void delete(long id) {
		Author a =  authorRepository.findById(id)
				.orElseThrow(() -> {throw new AuthorNotFoundException(id);});
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
	public List<AuthorDomain> readAll(AuthorFilterDTO filter) {
		Specification<Author> specification = Specification
				.where(AuthorSpecification.lesThenBookRating(filter.getMaxRating()))
				.and(AuthorSpecification.likeName(filter.getAuthor()))
				.and(AuthorSpecification.likeBook(filter.getBook()))
				.and(AuthorSpecification.likeFilms(filter.getFilm()));
		List<Author> authors;
		if (filter.getPage() != null && filter.getPageSize() != null) {
			Pageable page = PageRequest.of(filter.getPage(), filter.getPageSize());
			authors = authorRepository.findAll(specification, page).toList();
		} else {
			authors = authorRepository.findAll(specification);
		}
		List<AuthorDomain> authorDomains = authors.stream().map(authorDomainMapper::authorToDomain).collect(Collectors.toList());
		authorDomains.forEach(domain -> domain.setBookIds(domain.getBooks().stream().map(Book::getId).collect(Collectors.toList())));
		authorDomains.forEach(domain -> domain.setFilmIds(domain.getFilms().stream().map(Film::getId).collect(Collectors.toList())));
		return authorDomains;
	}
}
