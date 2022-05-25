package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewRequestDTO;
import ru.lexa.books_reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;


/**
 * Маппер отзывов для фильмов
 */
@Mapper(componentModel = "spring")
public interface FilmReviewMapper {
	/**
	 * Преобразовывает сущность отзыва в dto отзыва
	 *
	 * @param review - сущность отзыва
	 * @return - dto отзыва
	 */
	FilmReviewDTO reviewToDto(ReviewDomain review);

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @param film - фильм отзыва
	 * @return - сущность книги
	 */
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "bookId", ignore = true)
	@Mapping(target = "book", ignore = true)
	ReviewDomain dtoToReview(FilmReviewDTO dto);

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @param film - фильм отзыва
	 * @return - сущность книги
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "film", ignore = true)
	@Mapping(target = "bookId", ignore = true)
	@Mapping(target = "book", ignore = true)
	ReviewDomain dtoToReview(FilmReviewRequestDTO dto);

}
