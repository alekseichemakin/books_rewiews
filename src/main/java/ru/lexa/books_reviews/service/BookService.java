package ru.lexa.books_reviews.service;


import ru.lexa.books_reviews.model.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);

    List<Book> readAll();

    Book read(long id);

    Book update(Book book);

    void delete(long id);

    double averageRating(long id);

    List<Book> findByName(String name);

    List<Book> findByAuthor(String author);

    List<Book> findByDescription(String description);

    List<Book> findByReviewText(String text);
}
