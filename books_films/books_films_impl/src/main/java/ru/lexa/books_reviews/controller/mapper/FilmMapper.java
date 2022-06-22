package ru.lexa.books_reviews.controller.mapper;

import controller.dto.film.FilmDTO;
import controller.dto.film.FilmRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
	FilmDomain dtoToFilm(FilmDTO dto);

	/**
	 * Преобразовывает dto фильма в domain фильма
	 *
	 * @param dto - dto фильма
	 * @return - domain фильма
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "authors", ignore = true)
	FilmDomain dtoToFilm(FilmRequestDTO dto);
}
