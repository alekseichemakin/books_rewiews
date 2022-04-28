package ru.lexa.books_reviews.controller;

import ru.lexa.books_reviews.dto.BookDTO;
import ru.lexa.books_reviews.dto.ReviewDTO;

import java.util.Collection;

public interface BookController {

    BookDTO createBook(BookDTO dto);

    Collection<BookDTO> readAll(String author, String description, String name, String reviewText);

    BookDTO readBook(long id);

    BookDTO updateBook(BookDTO dto, long id);

    void deleteBook(long id);

    Collection<ReviewDTO> getReviews(long id);

    double getAverage(long id);
}
