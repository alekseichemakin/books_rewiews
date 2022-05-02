package ru.lexa.books_reviews.controller;

import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;

import javax.validation.Valid;
import java.util.Collection;

public interface BookController {

    BookDTO createBook(@Valid BookDTO dto);

    Collection<BookDTO> readAll(String author, String description, String name, String reviewText);

    BookDTO readBook(long id);

    BookDTO updateBook(BookDTO dto, long id);

    void deleteBook(long id);

    Collection<ReviewDTO> getReviews(long id);

    double getAverage(long id);
}
