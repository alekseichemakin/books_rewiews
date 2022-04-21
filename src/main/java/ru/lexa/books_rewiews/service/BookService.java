package ru.lexa.books_rewiews.service;


import ru.lexa.books_rewiews.model.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);

    List<Book> readAll();

    Book read(long id);

    Book update(Book book);

    boolean delete(long id);

    Book updateBookName(Book book);

    Book updateBookDesc(Book book);

    Book updateBookAuthor(Book book);
}
