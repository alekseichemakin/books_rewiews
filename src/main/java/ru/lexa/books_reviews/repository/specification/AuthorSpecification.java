package ru.lexa.books_reviews.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.repository.entity.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class AuthorSpecification {
	/**
	 * @return спецификацию для поиска по части имени
	 */
	public static Specification<Author> likeName(String name) {
		if (name == null)
			return null;
		return (root, query, cb) -> cb.like(root.get(Author_.NAME), "%" + name + "%");
	}

	/**
	 * @return спецификацию для поиска по части названия книги
	 */
	public static Specification<Author> likeBook(String book) {
		if (book == null)
			return null;
		return (root, query, cb) -> {
			Join<Author, Book> bookJoin = root.join(Author_.BOOKS);
			query.distinct(true);
			return cb.like(bookJoin.get(Book_.NAME), "%" + book + "%");
		};
	}

	/**
	 * @return спецификацию для поиска по части названия фильма
	 */
	public static Specification<Author> likeFilms(String film) {
		if (film == null)
			return null;
		return (root, query, cb) -> {
			Join<Author, Film> filmJoin = root.join(Author_.FILMS);
			query.distinct(true);
			return cb.like(filmJoin.get(Film_.NAME), "%" + film + "%");
		};
	}

	/**
	 * @return спецификацию для поиска по ретйтингу, меньше переданному
	 */
	public static Specification<Author> lesThenBookRating(Double maxRating) {
		if (maxRating == null)
			return null;
		return (root, query, cb) -> {
			Join<Author, Book> bookJoin = root.join(Author_.BOOKS);
			Join<Book, Review> rev = bookJoin.join(Book_.REVIEWS, JoinType.INNER);
			query.groupBy(root, bookJoin);
			query.having(cb.lessThan(cb.avg(rev.get(Review_.RATING)), maxRating))
					.distinct(true);
			return query.getRestriction();
		};
	}
}
