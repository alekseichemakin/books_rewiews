package ru.lexa.books_reviews.reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.reviews.repository.entity.Author;
import ru.lexa.books_reviews.reviews.repository.entity.Book;
import ru.lexa.books_reviews.reviews.repository.entity.Review;

import java.sql.Date;
import java.util.Collection;

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
	 * Авторы экранизированной книги
	 */
	private Collection<Author> authors;

	/**
	 * Список отзывов к фильму
	 */
	private Collection<Review> reviews;

	/**
	 * Дата экранизации
	 */
	private Date dateRelease;

	/**
	 * Экранизированная книга
	 */
	private Book book;

}
