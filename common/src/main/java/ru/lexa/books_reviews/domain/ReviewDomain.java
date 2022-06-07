package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

/**
 * Domain отзыва {@link ru.lexa.books_reviews.repository.entity.Review}
 */
@Data
public class ReviewDomain {
	/**
	 * Id отзыва
	 */
	private long id;

	/**
	 * Текст отзыва
	 */
	private String text;

	/**
	 * Рейтинг
	 */
	private int rating;

	/**
	 * Книга к которой принадледит отзыв
	 */
	private Book book;

	/**
	 * Фильм к которой принадледит отзыв
	 */
	private Film film;

	/**
	 * Id фильма
	 */
	private long filmId;

	/**
	 * Id книги
	 */
	private long bookId;
}