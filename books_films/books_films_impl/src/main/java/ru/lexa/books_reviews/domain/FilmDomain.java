package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;

import java.sql.Date;
import java.util.List;

/**
 * Domain автора {@link ru.lexa.books_reviews.repository.entity.Author}
 */
@Data
public class FilmDomain {
	/**
	 * Id фильма
	 */
	private  long id;

	/**
	 * Название фильма
	 */
	private String name;

	/**
	 * Дата экранизации
	 */
	private Date dateRelease;

	/**
	 * Экранизированная книга
	 */
	private Book book;

}
