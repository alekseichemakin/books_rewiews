package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.Collection;
import java.util.List;

/**
 * Domain книги {@link ru.lexa.books_reviews.repository.entity.Book}
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
	 * Авторы книги
	 */
	private List<Author> authors;

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
