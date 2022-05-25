package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

@Data
public class ReviewDomain {
	private long id;

	private String text;

	private int rating;

	private Book book;

	private Film film;

	private long filmId;

	private long bookId;
}
