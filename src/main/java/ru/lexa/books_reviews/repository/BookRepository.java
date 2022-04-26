package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import ru.lexa.books_reviews.model.Book;

import java.util.List;

//RevisionRepository<Book, Long, Long>
public interface BookRepository extends CrudRepository<Book, Long> {
    @Override
    List<Book> findAll();

    @Query(value = "select b from Book b, Review r where b.id = r.book.id and r.text like %:param%")
    List<Book> findBooksByReviewText(@Param("param") String text);
}