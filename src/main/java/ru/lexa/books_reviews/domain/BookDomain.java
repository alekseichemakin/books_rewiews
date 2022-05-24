package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;

import java.util.Collection;
import java.util.List;

@Data
public class BookDomain {
	private long id;

	private String name;

	private String description;

	private Collection<Review> reviews;

	private Collection<Author> authors;

	private Collection<Film> films;

	private List<Long> authorIds;

	private int reviewCount;

	private double averageRating;
}
