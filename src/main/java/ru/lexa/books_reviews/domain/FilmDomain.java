package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;

import java.sql.Date;
import java.util.Collection;

@Data
public class FilmDomain {
	private  long id;

	private String name;

	private Collection<Author> authors;

	private Collection<Review> reviews;

	private Date dateRelease;

	private Book book;

	private long bookId;

}
