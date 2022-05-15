package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewRequestDTO;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;

@Mapper(componentModel = "spring")
public interface FilmReviewMapper {
	@Mapping(target = "filmId", source = "review.film.id")
	FilmReviewDTO reviewToDto(Review review);

	@Mapping(target = "film", source = "film")
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "id", source = "dto.id")
	Review dtoToReview(FilmReviewDTO dto, Film film);

	@Mapping(target = "film", source = "film")
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "id", ignore = true)
	Review dtoToReview(FilmReviewRequestDTO dto, Film film);

}
