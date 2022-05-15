package ru.lexa.books_reviews.service.implementation;


import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.specification.BookSpecification;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.BookService}
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private FilmService filmService;

    @Override
    public Book create(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
    }

    @Override
    //TODO вынести в общий Exception  - InputErrorException("Нет книги с данным id")
    public Book read(long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @Override
    public Book update(Book book) {
        book.setReview(bookRepository.findById(book.getId())
                .orElseThrow(BookNotFoundException::new)
                .getReview());
        Collection<Film> films = book.getFilms();
        films.forEach(film -> film.setAuthor(book.getAuthor()));
        films = films.stream().map(filmService::update).collect(Collectors.toList());
        book.setFilms(films);
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
    }

    @Override
    public double averageRating(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        if (book.getReview() == null || book.getReview().size() == 0)
            return 0;
        //TODO check
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
