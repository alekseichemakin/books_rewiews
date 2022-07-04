package ru.lexa.books_reviews.service.implementation;

import controller.dto.book.BookFilterDTO;
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
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.integration.service.ReviewsService;
import ru.lexa.books_reviews.repository.AuthorBookRepository;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.repository.specification.BookSpecification;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.BookService}
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;

    private final AuthorBookRepository authorBookRepository;

    private final BookRepository bookRepository;

    private final BookDomainMapper bookDomainMapper;

    private final ReviewsService reviewsService;

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
        book.getAuthors().forEach(a -> authorService.read(a.getId()));

        try {
            book = bookDomainMapper.bookToDomain(bookRepository.save(bookDomainMapper.domainToBook(book)));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }

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
                .orElseThrow(() -> {
                    throw new BookNotFoundException(id);
                }));
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
        Double rating = reviewsService.getBookAverageRating(id);
        return rating == null ? 0 : rating;
    }

    @Transactional
    @Override
    @CachePut(value = "books", key = "#book.id")
    public BookDomain update(BookDomain book) {
        BookDomain finalBook = book;
        Book updatableBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> {
                    throw new BookNotFoundException(finalBook.getId());
                });

        List<Long> authorIds = book.getAuthors().stream().map(Author::getId).toList();
        updatableBook.getAuthors().forEach(author -> {
            if (!authorIds.contains(author.getId())) {
                updatableBook.deleteAuthor(author);
            }
        });
        List<Long> updatableAuthorIds = updatableBook.getAuthors().stream().map(Author::getId).toList();
        book.getAuthors().forEach(author -> {
            if (!updatableAuthorIds.contains(author.getId())) {
                updatableBook.addAuthor(author);
            }
        });

        updatableBook.setDescription(book.getDescription());
        updatableBook.setName(book.getName());
        updatableBook.getAuthors().forEach(author -> System.out.println(author.getId()));
        updatableBook.getAuthorBook().forEach(authorBook -> System.out.println(authorBook.getAuthor().getId() + " " + authorBook.getBook().getId()));

        try {
            book = prepareBookToReturn(bookRepository.saveAndFlush(updatableBook));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        return book;
    }

    @Override
    public List<BookDomain> readAll(BookFilterDTO filter) {
        Specification<Book> spec = Specification
                .where(BookSpecification.likeName(filter.getName()))
                .and(BookSpecification.likeAuthor(filter.getAuthor()))
                .and(BookSpecification.likeDescription(filter.getDescription()));
        if (filter.getReviewText() != null && !filter.getReviewText().isEmpty()) {
            spec = spec.and(BookSpecification.byIds(reviewsService.getBookIdsWhereReviewText(filter.getReviewText()).stream().toList()));
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
        bookDomain.setReviewCount(reviewsService.getBookReviews(book.getId()).size());
        return bookDomain;
    }
}
