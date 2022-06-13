package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.Collection;

/**
 * Domain автора {@link ru.lexa.books_reviews.repository.entity.Author}
 */
@Data
public class AuthorDomain {

	/**
	 * Id автора
	 */
	private long id;

	/**
	 * Имя автора
	 */
	private String name;

	/**
	 * Книги написанные автором
	 */
	private Collection<Book> books;

	/**
	 * Фильмы экранизированные по книгам данного автора
	 */
	private Collection<Film> films;

	/**
	 * Средний рейтинг автора
	 */
	private Double avgRating;
}
