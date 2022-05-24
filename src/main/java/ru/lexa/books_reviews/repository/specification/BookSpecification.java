package ru.lexa.books_reviews.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.repository.entity.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

/**
 * Спецификация для поиска книг в БД
 */
public class BookSpecification {
	/**
	 * @return спецификацию для поиска по части названия
	 */
	public static Specification<Book> likeName(String name) {
		if (name == null)
			return null;
		return (root, query, cb) -> cb.like(root.get(Book_.NAME), "%" + name + "%");
	}

	/**
	 * @return спецификацию для поиска по части имени автора
	 */
	public static Specification<Book> likeAuthor(String author) {
		if (author == null)
			return null;
		return (root, query, cb) -> {
			Join<Book, Author> authorJoin = root.join(Book_.AUTHORS);
			return cb.like(authorJoin.get(Author_.NAME), "%" + author + "%");
		};
	}

	/**
	 * @return спецификацию для поиска по части описания
	 */
	public static Specification<Book> likeDescription(String text) {
		if (text == null)
			return null;
		return (root, query, cb) -> cb.like(root.get(Book_.DESCRIPTION), "%" + text + "%");
	}

	/**
	 * @return спецификацию для поиска по части текста отзыва
	 */
	public static Specification<Book> likeReviewText(String text) {
		if (text == null)
			return null;
		return (root, query, cb) -> {
			Join<Book, Review> rev = root.join(Book_.REVIEWS);
			return cb.like(rev.get(Review_.TEXT), "%" + text + "%");
		};
	}

	/**
	 * @return спецификацию для поиска по ретйтингу, меньше переданному
	 */
	public static Specification<Book> lesThenRating(Double maxRating) {
		if (maxRating == null)
			return null;
		return (root, query, cb) -> {
			Join<Book, Review> rev = root.join(Book_.REVIEWS, JoinType.INNER);       //	SELECT b FROM book b INNER JOIN review r ON b.id = r.book_id
			query.groupBy(root);                                                    //	GROUP BY b
			query.having(cb.lessThan(cb.avg(rev.get(Review_.RATING)), maxRating));  //	having avg(rating) > ?
			return query.getRestriction();
		};
	}
}
