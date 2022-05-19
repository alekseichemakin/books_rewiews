package ru.lexa.books_reviews.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Book_;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.repository.entity.Review_;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

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
//		return (root, query, cb) -> cb.like(root.get(Book_.author.getName()), "%" + author + "%");
		return null;
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
			Join<Book, Review> rev = root.join(Book_.REVIEW);
			return cb.like(rev.get(Review_.TEXT), "%" + text + "%");
		};
	}

	//TODO improve
	/**
	 * @return спецификацию для поиска по ретйтингу, меньше переданному
	 */
	public static Specification<Book> lesThenRating(Double maxRating) {
		if (maxRating == null)
			return null;
		return (root, query, cb) -> {
			return null;
		};
	}
//	select b
//	from (SELECT b, avg(r.rating) rat
//	FROM review r,
//	book b
//	WHERE b.id = r.book_id
//	GROUP BY b)
//	as result
//	where rat > 5;
}
