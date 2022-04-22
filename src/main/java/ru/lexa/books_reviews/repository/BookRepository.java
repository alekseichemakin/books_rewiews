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
    List<Book> findBooksByAuthorContainingIgnoreCase(String author);
    List<Book> findBooksByNameContainingIgnoreCase(String name);
    List<Book> findBooksByDescriptionContainingIgnoreCase(String description);

    @Query(value = "select Book from Book, Review where Book.id = Review.book_id and Review.text like '%str%'")
    List<Book> findBooksByReviewText(@Param("t") String text);
}