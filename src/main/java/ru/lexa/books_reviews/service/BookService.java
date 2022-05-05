package ru.lexa.books_reviews.service;


import ru.lexa.books_reviews.controller.dto.BookFilterDTO;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);

    Book read(long id);

    Book update(Book book, long id);

    void delete(long id);

    double averageRating(long id);

    List<Book> readAll(BookFilterDTO filter);
}
