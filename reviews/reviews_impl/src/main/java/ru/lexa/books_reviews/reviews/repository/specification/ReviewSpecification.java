package ru.lexa.books_reviews.reviews.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.reviews.repository.entity.Review;
import ru.lexa.books_reviews.reviews.repository.entity.Review_;

/**
 * Спецификация для поиска отзывов в БД
 */
public class ReviewSpecification {
    /**
     * @return спецификацию для поиска по части названия
     */
    public static Specification<Review> likeText(String text) {
        if (text == null || text.isEmpty())
            return null;
        return (root, query, cb) -> cb.like(root.get(Review_.TEXT), "%" + text + "%");
    }

    /**
     * @return спецификацию для поиска по Id книги
     */
    public static Specification<Review> whereBookId(Long id) {
        if (id == null)
            return null;
        return (root, query, cb) -> cb.equal(root.get(Review_.BOOK_ID), id);
    }

    /**
     * @return спецификацию для поиска отзывов к книгам
     */
    public static Specification<Review> whereBookIdNotNull() {
        return (root, query, cb) -> cb.isNotNull(root.get(Review_.BOOK_ID));
    }

    /**
     * @return спецификацию для поиска отзывов к фильмам
     */
    public static Specification<Review> whereFilmIdNotNull() {
        return (root, query, cb) -> cb.isNotNull(root.get(Review_.BOOK_ID));
    }
}
