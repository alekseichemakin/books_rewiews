package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewRequestDTO;
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
	@Mapping(target = "filmId", source = "review.film.id")
	FilmReviewDTO reviewToDto(Review review);

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @param film - фильм отзыва
	 * @return - сущность книги
	 */
	@Mapping(target = "film", source = "film")
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "id", source = "dto.id")
	Review dtoToReview(FilmReviewDTO dto, Film film);

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @param film - фильм отзыва
	 * @return - сущность книги
	 */
	@Mapping(target = "film", source = "film")
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "id", ignore = true)
	Review dtoToReview(FilmReviewRequestDTO dto, Film film);

}
