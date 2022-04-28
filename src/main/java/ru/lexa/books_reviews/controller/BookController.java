package ru.lexa.books_reviews.controller;

import ru.lexa.books_reviews.dto.BookDTO;
import ru.lexa.books_reviews.dto.ReviewDTO;

import java.util.Collection;

public interface BookController {

    public BookDTO createBook(BookDTO dto);

    public Collection<BookDTO> readAll(String author, String description, String name, String reviewText);

    public BookDTO readBook(long id);

    public BookDTO updateBook(BookDTO dto, long id);

    public void deleteBook(long id);

    public Collection<ReviewDTO> getReviews(long id);

    double getAverage(long id);
}
