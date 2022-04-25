package ru.lexa.books_reviews.service;


import ru.lexa.books_reviews.model.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book create(Book book);

    Book read(long id);

    Book update(Book book);

    void delete(long id);

    double averageRating(long id);

    List<Book> readFilter(Map<String, String> params);
}
