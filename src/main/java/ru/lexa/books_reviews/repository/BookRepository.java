package ru.lexa.books_reviews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import ru.lexa.books_reviews.model.Book;

import java.util.List;

//RevisionRepository<Book, Long, Long>
public interface BookRepository extends CrudRepository<Book, Long> {
    @Override
    List<Book> findAll();
}