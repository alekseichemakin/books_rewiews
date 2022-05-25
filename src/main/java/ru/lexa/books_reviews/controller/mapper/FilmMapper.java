package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmRequestDTO;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.Collection;
import java.util.Set;

/**
 * Маппер фильмов
 */
@Mapper(componentModel = "spring")
public interface FilmMapper {
	/**
	 * Преобразовывает сущность фильма в dto фильма
	 *
	 * @param film - сущность фильма
	 * @return - dto фильма
	 */
	@Mapping(target = "bookId", source = "book.id")
	FilmDTO filmToDto(FilmDomain film);

	/**
	 * Преобразовывает dto фильма в сущность фильма
	 *
	 * @param dto - dto фильма
	 * @param author - автор фильма
	 * @param book - экранизированная книга
	 * @return - сущность фильма
	 */
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "authors", ignore = true)
	@Mapping(target = "reviews", ignore = true)
	FilmDomain dtoToFilm(FilmDTO dto);

	/**
	 * Преобразовывает dto фильма в сущность фильма
	 *
	 * @param dto - dto фильма
	 * @param author - автор фильма
	 * @param book - экранизированная книга
	 * @return - сущность фильма
	 */
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "authors", ignore = true)
	FilmDomain dtoToFilm(FilmRequestDTO dto);
}
