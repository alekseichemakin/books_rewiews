package ru.lexa.books_reviews.reviews.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.reviews.repository.entity.Review;

import java.util.List;

/**
 * Класс работающий с таблицей review
 */
public interface ReviewRepository extends CrudRepository<Review, Long>, JpaSpecificationExecutor<Review> {
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
    @Query(value = "SELECT r FROM Review r WHERE r.bookId is not null ")
    List<Review> findAllBooksReviews();

    /**
     * Возвращает список отзывов для фильмов
     *
     * @return список отзывов
     */
    @Query(value = "SELECT r FROM Review r WHERE r.filmId is not null")
    List<Review> findAllFilmsReviews();

    /**
     * Возвращает средний рейтинг книги
     *
     * @return средний рейтинг
     */
    @Query("select avg(r.rating) from Review r where r.bookId = ?1")
    Double getAverageRating(long id);
}
