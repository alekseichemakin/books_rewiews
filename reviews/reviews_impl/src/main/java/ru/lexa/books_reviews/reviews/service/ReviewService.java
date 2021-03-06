package ru.lexa.books_reviews.reviews.service;

import ru.lexa.books_reviews.controller.dto.review.ReviewFilterDTO;
import ru.lexa.books_reviews.reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.reviews.exception.ReviewNotFoundException;

import java.util.List;

/**
 * Сервис обрабатывающий запорсы с контроллера {@link ru.lexa.books_reviews.controller.BookReviewController} {@link ru.lexa.books_reviews.controller.FilmReviewController}
 */
public interface ReviewService {
	/**
	 * Создает отзыв к книге
	 *
	 * @param review - отзыв для создания
	 * @return созданный отзыв
	 * @throws BookNotFoundException, если нет книги с данным ID
	 * @throws FilmNotFoundException, если нет фильма с данным ID
	 */
	ReviewDomain create(ReviewDomain review);

	/**
	 * Возвращает список всех имеющихся отзывов
	 *
	 * @return список отзывов
	 */
	List<ReviewDomain> readAll();

	/**
	 * Возвращает отзыв по ID
	 *
	 * @param id - ID отзыва
	 * @return - объект отзыва с заданным ID
	 * @throws ReviewNotFoundException, если нет отзыва с данным ID
	 */
	ReviewDomain read(long id);

	/**
	 * Обновляет отзыв с заданным ID,
	 * в соответствии с переданным отзывом
	 *
	 * @param review - отзыв в соответсвии с которым нужно обновить данные
	 * @return - объект обновленного отзыва
	 * @throws ReviewNotFoundException, если нет отзыва с данным ID
	 */
	ReviewDomain update(ReviewDomain review);

	/**
	 * Удаляет отзыв с заданным ID
	 *
	 * @param id - id отзыва, которого нужно удалить
	 * @throws ReviewNotFoundException, если нет отзыва с данным ID
	 */
	void delete(long id);

	/**
	 * Возвращает список всех имеющихся отзывов для книг
	 *
	 * @return список отзывов
	 */
	List<ReviewDomain> readAllBooksReviews(ReviewFilterDTO filterDTO);

	/**
	 * Возвращает список всех имеющихся отзывов для фильмов
	 *
	 * @return список отзывов
	 */
	List<ReviewDomain> readAllFilmsReviews();

	/**
	 * Возвращает средний рейтинг книги
	 *
	 * @return средний рейтинг
	 */
	Double getBookAverageRating(long bookId);

	/**
	 * Удаление отзывов к книге
	 *
	 * @param bookId - id книги
	 */
	void deleteReviewsForBook(long bookId);

	/**
	 * Удаление отзывов к фильму
	 *
	 * @param filmId - id фильма
	 */
	void deleteReviewsForFilm(long filmId);
}
