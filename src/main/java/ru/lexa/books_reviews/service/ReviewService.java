package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.repository.entity.Review;

import java.util.List;

/**
 * Сервис обрабатывающий запорсы с контроллера {@link ru.lexa.books_reviews.controller.ReviewController}
 */
public interface ReviewService {
    /**
     * Создает отзыв к книге
     * @param review - отзыв для создания
     * @return созданный отзыв
     * @throws InputErrorException, если нет книги с данным ID
     */
    Review create(Review review);

    /**
     * Возвращает список всех имеющихся отзывов
     * @return список книг
     */
    List<Review> readAll();

    /**
     * Возвращает отзыв по ID
     * @param id - ID отзыва
     * @return - объект отзыва с заданным ID
     * @throws InputErrorException, если нет отзыва с данным ID
     */
    Review read(long id);

    /**
     * Обновляет отзыв с заданным ID,
     * в соответствии с переданным отзывом
     * @param review - отзыв в соответсвии с которым нужно обновить данные
     * @return - объект обновленного отзыва
     * @throws InputErrorException, если нет отзыва с данным ID
     */
    Review update(Review review);

    /**
     * Удаляет отзыв с заданным ID
     * @param id - id отзыва, которого нужно удалить
     * @throws InputErrorException, если нет отзыва с данным ID
     */
    void delete(long id);

    List<Review> readAllBooksReviews();

    List<Review> readAllFilmsReviews();
}
