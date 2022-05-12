package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.Review;

import java.util.List;

/**
 * Класс работающий с таблицей review
 */
public interface ReviewRepository extends CrudRepository<Review, Long> {
    /**
     * Возвращает список всех имеющихся отзывов
     *
     * @return список отзывов
     */
    @Override
    List<Review> findAll();

    @Query(value = "SELECT r FROM Review r WHERE r.book is not null ")
    List<Review> findAllBooksReviews();

    @Query(value = "SELECT r FROM Review r WHERE r.film is not null")
    List<Review> findAllFilmsReviews();
}
