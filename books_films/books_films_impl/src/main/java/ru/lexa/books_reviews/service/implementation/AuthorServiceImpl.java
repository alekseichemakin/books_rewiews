package ru.lexa.books_reviews.service.implementation;

import controller.dto.author.AuthorFilterDTO;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.exception.AuthorNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.AuthorBookRepository;
import ru.lexa.books_reviews.repository.AuthorRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.AuthorBook;
import ru.lexa.books_reviews.repository.entity.Book;
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
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

	private AuthorRepository authorRepository;

	private BookService bookService;

	private AuthorDomainMapper authorDomainMapper;

	private AuthorBookRepository authorBookRepository;

	@Transactional
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
		AuthorDomain author = authorDomainMapper.authorToDomain(authorRepository.findById(id)
				.orElseThrow(() -> {
					throw new AuthorNotFoundException(id);
				}));
		author.setAvgRating(getAverageRating(id));
		return author;
	}

	@Transactional
	@Override
	public AuthorDomain update(AuthorDomain author) {
		author.setBooks(read(author.getId()).getBooks());
		author.setFilms(read(author.getId()).getFilms());
		try {
			author = authorDomainMapper
					.authorToDomain(authorRepository.save(authorDomainMapper
							.domainToAuthor(author)));
			author.setAvgRating(getAverageRating(author.getId()));
			return author;
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Transactional
	@Override
	public void delete(long id) {
		Author a = authorRepository.findById(id)
				.orElseThrow(() -> {
					throw new AuthorNotFoundException(id);
				});
		for (Book b : a.getBooks()) {
			authorBookRepository.delete(new AuthorBook(a, b));
			if (b.getAuthors().size() == 1) {
				bookService.delete(b.getId());
			}
		}
		authorRepository.delete(a);
	}

	@Override
	public List<AuthorDomain> readAll(AuthorFilterDTO filter) {
		Specification<Author> specification = Specification
				.where(AuthorSpecification.likeName(filter.getAuthor()))
				.and(AuthorSpecification.likeBook(filter.getBook()))
				.and(AuthorSpecification.likeFilms(filter.getFilm()));
		List<Author> authors;
		if (filter.getPage() != null && filter.getPageSize() != null) {
			Pageable page = PageRequest.of(filter.getPage(), filter.getPageSize());
			authors = authorRepository.findAll(specification, page).toList();
		} else {
			authors = authorRepository.findAll(specification);
		}
		List<AuthorDomain> authorDomains = authors.stream()
				.map(author -> authorDomainMapper.authorToDomain(author))
				.peek(a -> a.setAvgRating(getAverageRating(a.getId())))
				.toList();
		if (filter.getMaxRating() != null) {
			authorDomains = authorDomains.stream()
					.filter(a -> a.getAvgRating() < filter.getMaxRating())
					.toList();
		}
		return authorDomains;
	}

	@Override
	public Double getAverageRating(long id) {
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> {
					throw new AuthorNotFoundException(id);
				});
		if (author.getBooks().size() == 0) {
			return 0.0;
		}
		return author.getBooks().stream()
				.map(book -> bookService.averageRating(book.getId()))
				.mapToDouble(Double::doubleValue)
				.sum() / author.getBooks().size();
	}
}
