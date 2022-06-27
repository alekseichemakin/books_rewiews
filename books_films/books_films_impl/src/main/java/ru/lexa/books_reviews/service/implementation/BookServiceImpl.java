package ru.lexa.books_reviews.service.implementation;

import controller.dto.book.BookFilterDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.configuration.RabbitMqConfig;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.ReviewFilterDTO;
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
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorBookRepository authorBookRepository;

    private final BookRepository bookRepository;

    private final BookDomainMapper bookDomainMapper;

    private final FilmDomainMapper filmDomainMapper;

    private final FilmService filmService;

    private final ReviewClient reviewClient;

    private final AmqpTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String EXCHANGE;
    @Value("${spring.rabbitmq.routing-key.createBookReview}")
    private String CREATE_BOOK_REVIEW_ROUTING_KEY;
    @Value("${spring.rabbitmq.routing-key.deleteBooksReview}")
    private String DELETE_BOOK_REVIEW_ROUTING_KEY;

    @Transactional
    @Override
    public BookDomain create(BookDomain book, BookReviewRequestDTO reviewRequestDTO) {
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

        if (reviewRequestDTO != null) {
            reviewRequestDTO.setBookId(book.getId());
            rabbitTemplate.convertAndSend(EXCHANGE, CREATE_BOOK_REVIEW_ROUTING_KEY, reviewRequestDTO);
        }
        return book;
    }

    @Cacheable(value = "books", key = "#id")
    @Override
    public BookDomain read(long id) {
        return prepareBookToReturn(bookRepository.findById(id)
                .orElseThrow(() -> { throw new BookNotFoundException(id); }));
    }

    @CacheEvict(value = "books", key = "#id")
    @Transactional
    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            throw new BookNotFoundException(id);
        });
        authorBookRepository.deleteAll(book.getAuthorBook());
        bookRepository.delete(book);
        rabbitTemplate.convertAndSend(EXCHANGE, DELETE_BOOK_REVIEW_ROUTING_KEY, id);
    }

    @Cacheable(value = "bookRating", key = "#id")
    @Override
    public double averageRating(long id) {
        Double rating = reviewClient.averageBookRating(id);
        return rating == null ? 0 : rating;
    }

    @Transactional
    @Override
    @CachePut(value = "books", key = "#book.id")
    public BookDomain update(BookDomain book) {
        List<Author> authors = book.getAuthors();
        BookDomain finalBook = book;
        Book updatableBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> {
                    throw new BookNotFoundException(finalBook.getId());
                });
        updatableBook.getAuthors().forEach(author -> {
            if (!authors.contains(author)) {
                authorBookRepository.delete(new AuthorBook(author, updatableBook));
            }
        });
        updatableBook.setAuthors(new ArrayList<>());
        updatableBook.setDescription(book.getDescription());
        updatableBook.setName(book.getName());
        Collection<Film> films = updatableBook.getFilms();
        films.forEach(film -> film.setAuthors(finalBook.getAuthors()));
        films.forEach(film -> filmService.update(filmDomainMapper.filmToDomain(film)));
        try {
            book = prepareBookToReturn(bookRepository.save(updatableBook));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        authors.forEach(author -> {
            AuthorBook authorBook = new AuthorBook(author, bookDomainMapper.domainToBook(finalBook));
            authorBookRepository.save(authorBook);
        });
        book.setAuthors(authors);
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
            reviewClient.readAll(new ReviewFilterDTO(filter.getReviewText(), null)).forEach(r -> ids.add(r.getBookId()));
            spec = spec.and(BookSpecification.byIds(ids.stream().toList()));
        }
        List<Book> books;
        if (filter.getPage() != null && filter.getPageSize() != null) {
            Pageable page = PageRequest.of(filter.getPage(), filter.getPageSize());
            books = bookRepository.findAll(spec, page).toList();
        } else {
            books = bookRepository.findAll(spec);
        }
        List<BookDomain> bookDomains = books.stream().map(this::prepareBookToReturn).collect(Collectors.toList());
        if (filter.getLessThenRating() != null) {
            bookDomains = bookDomains.stream()
                    .filter(d -> d.getAverageRating() < filter.getLessThenRating())
                    .collect(Collectors.toList());
        }
        return bookDomains;
    }

    @CacheEvict(value = {"books", "bookRating"}, key = "#id")
    public void clearCache(long id) {
    }

    private BookDomain prepareBookToReturn(Book book) {
        BookDomain bookDomain = bookDomainMapper.bookToDomain(book);
        bookDomain.setAverageRating(averageRating(book.getId()));
        bookDomain.setReviewCount(reviewClient.readAll(new ReviewFilterDTO(null, book.getId())).size());
        return bookDomain;
    }
}
