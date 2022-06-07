package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.repository.entity.Film;

/**
 * Маппер domain и сущности фильмов
 */
@Mapper(componentModel = "spring")
public interface FilmDomainMapper {
	/**
	 * Преобразовывает domain фильма в сущность фильма
	 *
	 * @param domain - domain фильма
	 * @return - сущность фильма
	 */
	Film domainToFilm(FilmDomain domain);

	/**
	 * Преобразовывает сущность фильма в domain фильма
	 *
	 * @param film - сущность фильма
	 * @return - domain фильма
	 */
	FilmDomain filmToDomain(Film film);
}
