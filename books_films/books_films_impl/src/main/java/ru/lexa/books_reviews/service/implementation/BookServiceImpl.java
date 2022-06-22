package ru.lexa.books_reviews.service.implementation;

import controller.dto.book.BookFilterDTO;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.integration.ReviewClient;
import ru.lexa.books_reviews.repository.AuthorBookRepository;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.AuthorBook;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.repository.specification.BookSpecification;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.BookService}
 */
@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private AuthorBookRepository authorBookRepository;

    private BookRepository bookRepository;

    private BookDomainMapper bookDomainMapper;

    private FilmDomainMapper filmDomainMapper;

    private FilmService filmService;

    private ReviewClient reviewClient;

    @Transactional
    @Override
    public BookDomain create(BookDomain book) {
        List<Author> authors = book.getAuthors();
        book.setAuthors(new ArrayList<>());
        try {
            book = bookDomainMapper.bookToDomain(bookRepository.save(bookDomainMapper.domainToBook(book)));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        BookDomain finalBook = book;
        authors.forEach(author -> authorBookRepository
                .save(new AuthorBook(author, bookDomainMapper.domainToBook(finalBook))));
        book.setAuthors(authors);
        return book;
    }

    @Override
    public BookDomain read(long id) {
        BookDomain domain = bookDomainMapper.bookToDomain(bookRepository.findById(id)
                .orElseThrow(() -> {
                    throw new BookNotFoundException(id);
                }));
        domain.setAverageRating(averageRating(id));
        domain.setReviewCount(reviewClient.getReviewsForBook(id).size());
        return domain;
    }

    @Transactional
    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            throw new BookNotFoundException(id);
        });
        authorBookRepository.deleteAll(book.getAuthorBook());
        bookRepository.delete(book);
    }

    @Override
    public double averageRating(long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> {
                    throw new BookNotFoundException(id);
                });
        Double rating = reviewClient.getAverageBookRating(id);
        return rating == null ? 0 : rating;
    }

    @Transactional
    @Override
    public BookDomain update(BookDomain book) {
        List<Author> authors = book.getAuthors();
        BookDomain finalBook = book;
        Book updatableBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> {
                    throw new BookNotFoundException(finalBook.getId());
                });
        updatableBook.setAuthors(new ArrayList<>());
        updatableBook.setDescription(book.getDescription());
        updatableBook.setName(book.getName());
        Collection<Film> films = updatableBook.getFilms();
        films.forEach(film -> film.setAuthors(finalBook.getAuthors()));
        films.forEach(film -> filmService.update(filmDomainMapper.filmToDomain(film)));
        try {
            book = bookDomainMapper.bookToDomain(bookRepository.save(updatableBook));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        authors.forEach(author -> {
            AuthorBook authorBook = new AuthorBook(author, bookDomainMapper.domainToBook(finalBook));
            authorBookRepository.save(authorBook);
        });
        book.setAuthors(authors);
        book.setAverageRating(averageRating(book.getId()));
        book.setReviewCount(reviewClient.getReviewsForBook(book.getId()).size());
        return book;
    }

    @Override
    public List<BookDomain> readAll(BookFilterDTO filter) {
        Specification<Book> spec = Specification
                .where(BookSpecification.likeName(filter.getName()))
                .and(BookSpecification.likeAuthor(filter.getAuthor()))
                .and(BookSpecification.likeDescription(filter.getDescription()));
        if (filter.getReviewText() != null && !filter.getReviewText().isEmpty()) {
            Set<Long> ids = new HashSet<>();
            reviewClient.getReviewsForText(filter.getReviewText()).forEach(r -> ids.add(r.getBookId()));
            spec = spec.and(BookSpecification.byIds(ids.stream().toList()));
        }
        List<Book> books;
        if (filter.getPage() != null && filter.getPageSize() != null) {
            Pageable page = PageRequest.of(filter.getPage(), filter.getPageSize());
            books = bookRepository.findAll(spec, page).toList();
        } else {
            books = bookRepository.findAll(spec);
        }
        List<BookDomain> bookDomains = books.stream().map(bookDomainMapper::bookToDomain).collect(Collectors.toList());
        bookDomains.forEach(domain -> domain.setReviewCount(reviewClient.getReviewsForBook(domain.getId()).size()));
        bookDomains.forEach(domain -> domain.setAverageRating(averageRating(domain.getId())));
        if (filter.getLessThenRating() != null) {
            bookDomains = bookDomains.stream()
                    .filter(d -> d.getAverageRating() < filter.getLessThenRating())
                    .collect(Collectors.toList());
        }
        return bookDomains;
    }
}
