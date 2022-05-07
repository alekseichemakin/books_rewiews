package ru.lexa.books_reviews.service.implementation;


import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.controller.dto.BookFilterDTO;
import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.specification.BookSpecification;
import ru.lexa.books_reviews.service.BookService;

import java.util.List;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.BookService}
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new InputErrorException("Неверное имя");
        }
    }

    @Override
    public Book read(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new InputErrorException("Нет книги с данным id"));
    }

    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new InputErrorException("Нет книги с данным id"));
        bookRepository.delete(book);
    }

    @Override
    public Book update(Book book) {
        book.setReview(bookRepository.findById(book.getId())
                .orElseThrow(() -> new InputErrorException("Нет книги с данным id"))
                .getReview());
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new InputErrorException("Неверное имя");
        }
    }

    @Override
    public double averageRating(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new InputErrorException("Нет книги с данным id"));

        if (book.getReview() == null || book.getReview().size() == 0)
            return 0;
        return book.getReview().stream().mapToDouble(Review::getRating).sum() / book.getReview().size();
    }

    @Override
    public List<Book> readAll(BookFilterDTO filter) {
        Specification<Book> spec = Specification
		        .where(BookSpecification.likeName(filter.getName()))
		        .and(BookSpecification.likeAuthor(filter.getAuthor()))
		        .and(BookSpecification.likeDescription(filter.getDescription()))
		        .and(BookSpecification.likeReviewText(filter.getReviewText()));
        return bookRepository.findAll(spec);
    }
}
