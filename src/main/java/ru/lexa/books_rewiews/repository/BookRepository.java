package ru.lexa.books_rewiews.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_rewiews.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Override
    List<Book> findAll();
}