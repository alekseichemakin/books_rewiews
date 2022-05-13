package ru.lexa.books_reviews.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Book_;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.repository.entity.Review_;

import javax.persistence.criteria.Join;

/**
 * Спецификация для поиска книг в БД
 */
public class BookSpecification {
	public static Specification<Book> likeName(String name) {
		if (name == null)
			return null;
		return (root, query, cb) -> cb.like(root.get(Book_.NAME), "%" + name + "%");
	}

	public static Specification<Book> likeAuthor(String author) {
		if (author == null)
			return null;
		return (root, query, cb) -> cb.like(root.get(Book_.author.getName()), "%" + author + "%");
	}

	public static Specification<Book> likeDescription(String text) {
		if (text == null)
			return null;
		return (root, query, cb) -> cb.like(root.get(Book_.DESCRIPTION), "%" + text + "%");
	}

	public static Specification<Book> likeReviewText(String text) {
		if (text == null)
			return null;
		return (root, query, cb) -> {
			Join<Book, Review> rev = root.join(Book_.REVIEW);
			return cb.like(rev.get(Review_.TEXT), "%" + text + "%");
		};
	}
}
