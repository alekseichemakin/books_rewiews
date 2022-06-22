package ru.lexa.books_reviews.reviews.domain;

import lombok.Data;

/**
 * Domain отзыва {@link ru.lexa.books_reviews.reviews.repository.entity.Review}
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
	 * Id фильма
	 */
	private Long filmId;

	/**
	 * Id книги
	 */
	private Long bookId;
}
