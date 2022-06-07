package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmRequestDTO;
import ru.lexa.books_reviews.domain.FilmDomain;

/**
 * Маппер domain и dto фильмов
 */
@Mapper(componentModel = "spring")
public interface FilmMapper {
	/**
	 * Преобразовывает domain фильма в dto фильма
	 *
	 * @param film - domain фильма
	 * @return - dto фильма
	 */
	@Mapping(target = "bookId", source = "book.id")
	FilmDTO filmToDto(FilmDomain film);

	/**
	 * Преобразовывает dto фильма в domain фильма
	 *
	 * @param dto - dto фильма
	 * @return - domain фильма
	 */
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "authors", ignore = true)
	@Mapping(target = "reviews", ignore = true)
	FilmDomain dtoToFilm(FilmDTO dto);

	/**
	 * Преобразовывает dto фильма в domain фильма
	 *
	 * @param dto - dto фильма
	 * @return - domain фильма
	 */
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "authors", ignore = true)
	FilmDomain dtoToFilm(FilmRequestDTO dto);
}
