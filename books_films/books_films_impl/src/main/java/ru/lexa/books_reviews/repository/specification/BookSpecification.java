package ru.lexa.books_reviews.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.repository.entity.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;

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
	 * @return спецификацию для поиска по id книг
	 */
	public static Specification<Book> byIds(List<Long> ids) {
		if (ids == null)
			return null;
		return (root, query, cb) -> {
			CriteriaBuilder.In<Long> inId = cb.in(root.get("id"));
			for (Long id : ids) {
				inId.value(id);
			}
			return query.where(inId).getRestriction();
		};
	}
}
