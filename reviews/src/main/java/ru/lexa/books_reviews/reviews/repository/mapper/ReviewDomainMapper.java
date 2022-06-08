package ru.lexa.books_reviews.reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.reviews.repository.entity.Review;

/**
 * Маппер сущности и domain отзывов
 */
@Mapper(componentModel = "spring")
public interface ReviewDomainMapper {
	/**
	 * Преобразовывает domain отзыва в сущность отзыва
	 *
	 * @param domain - domain отзыва
	 * @return - сущность отзыва
	 */
	Review domainToReview(ReviewDomain domain);

	/**
	 * Преобразовывает сущность отзыва в domain отзыва
	 *
	 * @param review - сущность отзыва
	 * @return - domain книги
	 */
	@Mapping(target = "filmId", ignore = true)
	@Mapping(target = "bookId", ignore = true)
	ReviewDomain reviewToDomain(Review review);
}
