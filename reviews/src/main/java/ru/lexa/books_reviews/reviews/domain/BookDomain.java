package ru.lexa.books_reviews.reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.reviews.repository.entity.Author;
import ru.lexa.books_reviews.reviews.repository.entity.Film;
import ru.lexa.books_reviews.reviews.repository.entity.Review;

import java.util.Collection;

/**
 * Domain книги {@link ru.lexa.books_reviews.reviews.repository.entity.Book}
 */
@Data
public class BookDomain {
	/**
	 * Id книги
	 */
	private long id;

	/**
	 * Название книги
	 */
	private String name;

	/**
	 * Описание книги
	 */
	private String description;

	/**
	 * Отзывы книги
	 */
	private Collection<Review> reviews;

	/**
	 * Авторы книги
	 */
	private Collection<Author> authors;

	/**
	 * Экранизации книги
	 */
	private Collection<Film> films;

	/**
	 * Колличество отзывов
	 */
	private int reviewCount;

	/**
	 * Средний рейтинг
	 */
	private double averageRating;
}
