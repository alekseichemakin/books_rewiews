package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmRequestDTO;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

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
	FilmDTO filmToDto(Film film);

	/**
	 * Преобразовывает dto фильма в сущность фильма
	 *
	 * @param dto - dto фильма
	 * @param author - автор фильма
	 * @param book - экранизированная книга
	 * @return - сущность фильма
	 */
	@Mapping(target = "dateRelease", source = "dto.dateRelease")
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "book", source = "book")
	Film dtoToFilm(FilmDTO dto, Author author, Book book);

	/**
	 * Преобразовывает dto фильма в сущность фильма
	 *
	 * @param dto - dto фильма
	 * @param author - автор фильма
	 * @param book - экранизированная книга
	 * @return - сущность фильма
	 */
	@Mapping(target = "dateRelease", source = "dto.dateRelease")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "author", source = "author")
	@Mapping(target = "book", source = "book")
	Film dtoToFilm(FilmRequestDTO dto, Author author, Book book);
}
