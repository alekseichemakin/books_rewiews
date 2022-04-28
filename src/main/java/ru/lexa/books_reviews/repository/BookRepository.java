package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import ru.lexa.books_reviews.model.Book;

import java.util.List;

//RevisionRepository<Book, Long, Long>
public interface BookRepository extends CrudRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Override
    List<Book> findAll();
}