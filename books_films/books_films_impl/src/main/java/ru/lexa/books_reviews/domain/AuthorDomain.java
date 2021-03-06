package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private List<Book> books;

	/**
	 * Фильмы экранизированные по книгам данного автора
	 */
	private Collection<Film> films;

	/**
	 * Средний рейтинг автора
	 */
	private Double avgRating;
}
