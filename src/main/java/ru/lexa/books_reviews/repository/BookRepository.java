package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;

//RevisionRepository<Book, Long, Long>
public interface BookRepository extends CrudRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Override
    List<Book> findAll();
}