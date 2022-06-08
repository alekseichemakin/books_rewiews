package ru.lexa.books_reviews.reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.reviews.domain.ReviewDomain;

/**
 * Маппер dto и domain отзывов для книг
 */
@Mapper(componentModel = "spring")
public interface BookReviewMapper {
	/**
	 * Преобразовывает domain отзыва в dto отзыва
	 *
	 * @param review - domain отзыва
	 * @return - dto отзыва
	 */
	BookReviewDTO reviewToDto(ReviewDomain review);

	/**
	 * Преобразовывает dto отзыва в domain отзыва
	 *
	 * @param dto - dto отзыва
	 * @return - domain книги
	 */
	@Mapping(target = "filmId", ignore = true)
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "book", ignore = true)
	ReviewDomain dtoToReview(BookReviewDTO dto);

	/**
	 * Преобразовывает dto отзыва в domain отзыва
	 *
	 * @param dto - dto отзыва
	 * @return - domain книги
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "filmId", ignore = true)
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "book", ignore = true)
	ReviewDomain dtoToReview(BookReviewRequestDTO dto);
}
