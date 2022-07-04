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
import ru.lexa.books_reviews.repository.AuthorRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.repository.specification.AuthorSpecification;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.integration.service.ReviewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.AuthorService}
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    private ReviewsService reviewsService;

    private AuthorDomainMapper authorDomainMapper;

    @Transactional
    @Override
    public AuthorDomain create(AuthorDomain author) {
        try {
            return prepareAuthorToReturn(authorRepository.save(authorDomainMapper.domainToAuthor(author)));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
    }

    @Override
    public AuthorDomain read(long id) {
        return prepareAuthorToReturn(authorRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new AuthorNotFoundException(id);
                }));
    }

    @Transactional
    @Override
    public AuthorDomain update(AuthorDomain author) {
        author.setBooks(read(author.getId()).getBooks());
        author.setFilms(read(author.getId()).getFilms());
        try {
            return prepareAuthorToReturn(authorRepository.save(authorDomainMapper.domainToAuthor(author)));
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
                .map(this::prepareAuthorToReturn)
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
        if (author.getBooks() == null || author.getBooks().size() == 0) {
            return 0.0;
        }
        return reviewsService.getAuthorAverageRating(author);
    }

    private AuthorDomain prepareAuthorToReturn(Author author) {
        AuthorDomain authorDomain = authorDomainMapper.authorToDomain(author);
		authorDomain.setAvgRating(getAverageRating(authorDomain.getId()));
        List<Film> films = new ArrayList<>();
        author.getBooks().forEach(book -> films.addAll(book.getFilms()));
        authorDomain.setFilms(films);
        return authorDomain;
    }
}
