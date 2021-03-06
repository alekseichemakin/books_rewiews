package ru.lexa.books_reviews.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.repository.entity.*;

import javax.persistence.criteria.Join;

public class AuthorSpecification {
	/**
	 * @return спецификацию для поиска по части имени
	 */
	public static Specification<Author> likeName(String name) {
		if (name == null || name.isEmpty())
			return null;
		return (root, query, cb) -> cb.like(root.get(Author_.NAME), "%" + name + "%");
	}

	/**
	 * @return спецификацию для поиска по части названия книги
	 */
	public static Specification<Author> likeBook(String book) {
		if (book == null || book.isEmpty())
			return null;
		return (root, query, cb) -> {
			Join<Author, Book> bookJoin = root.join(Author_.BOOKS);
			query.distinct(true);
			return cb.like(bookJoin.get(AuthorBook_.BOOK).get(Book_.NAME), "%" + book + "%");
		};
	}

	/**
	 * @return спецификацию для поиска по части названия фильма
	 */
	public static Specification<Author> likeFilms(String film) {
		if (film == null)
			return null;
		return (root, query, cb) -> {
			Join<Author, Film> bookJoin = root.join(Author_.BOOKS).join(AuthorBook_.BOOK).join(Book_.FILMS);
			query.distinct(true);
			return cb.like(bookJoin.get(Film_.NAME), "%" + film + "%");
		};
	}
}
