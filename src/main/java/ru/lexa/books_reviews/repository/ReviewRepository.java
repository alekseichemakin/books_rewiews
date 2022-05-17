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

    /**
     * Возвращает список отзывов для книги
     *
     * @return список отзывов
     */
    @Query(value = "SELECT r FROM Review r WHERE r.book is not null ")
    List<Review> findAllBooksReviews();

    /**
     * Возвращает список отзывов для фильмоф
     *
     * @return список отзывов
     */
    @Query(value = "SELECT r FROM Review r WHERE r.film is not null")
    List<Review> findAllFilmsReviews();
}
