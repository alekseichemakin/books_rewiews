package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;

/**
 * Маппер отзывов для книг
 */
@Mapper(componentModel = "spring")
public interface BookReviewMapper {
	/**
	 * Преобразовывает сущность отзыва в dto отзыва
	 *
	 * @param review - сущность отзыва
	 * @return - dto отзыва
	 */
	@Mapping(target = "bookId", source = "review.book.id")
	BookReviewDTO reviewToDto(Review review);

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @param book - книга отзыва
	 * @return - сущность книги
	 */
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "book", source = "book")
	@Mapping(target = "id", source = "dto.id")
	Review dtoToReview(BookReviewDTO dto, Book book);

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @param book - книга отзыва
	 * @return - сущность книги
	 */
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "book", source = "book")
	@Mapping(target = "id", ignore = true)
	Review dtoToReview(BookReviewRequestDTO dto, Book book);
}
