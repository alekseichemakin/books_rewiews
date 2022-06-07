package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewRequestDTO;
import ru.lexa.books_reviews.domain.ReviewDomain;


/**
 * Маппер dto и domain отзывов для фильмов
 */
@Mapper(componentModel = "spring")
public interface FilmReviewMapper {
	/**
	 * Преобразовывает domain отзыва в dto отзыва
	 *
	 * @param review - domain отзыва
	 * @return - dto отзыва
	 */
	FilmReviewDTO reviewToDto(ReviewDomain review);

	/**
	 * Преобразовывает dto отзыва в domain отзыва
	 *
	 * @param dto - dto отзыва
	 * @return - domain книги
	 */
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "bookId", ignore = true)
	@Mapping(target = "book", ignore = true)
	ReviewDomain dtoToReview(FilmReviewDTO dto);

	/**
	 * Преобразовывает dto отзыва в domain отзыва
	 *
	 * @param dto - dto отзыва
	 * @return - domain книги
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "bookId", ignore = true)
	@Mapping(target = "book", ignore = true)
	ReviewDomain dtoToReview(FilmReviewRequestDTO dto);

}
