package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.repository.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewDomainMapper {
	Review domainToReview(ReviewDomain domain);

	@Mapping(target = "filmId", ignore = true)
	@Mapping(target = "bookId", ignore = true)
	ReviewDomain reviewToDomain(Review review);
}
