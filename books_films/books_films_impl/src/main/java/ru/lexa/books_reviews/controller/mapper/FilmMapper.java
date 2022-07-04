package ru.lexa.books_reviews.controller.mapper;

import controller.dto.film.FilmDTO;
import controller.dto.film.FilmRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

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
	@Mapping(source = "bookId", target = "book", qualifiedByName = "setBook")
//	@Mapping(target = "authors", ignore = true)
	FilmDomain dtoToFilm(FilmDTO dto);

	/**
	 * Преобразовывает dto фильма в domain фильма
	 *
	 * @param dto - dto фильма
	 * @return - domain фильма
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "bookId", target = "book", qualifiedByName = "setBook")
//	@Mapping(target = "authors", ignore = true)
	FilmDomain dtoToFilm(FilmRequestDTO dto);

	@Named("setBook")
	default Book setBook(Long bookId) {
		Book book = new Book();
		book.setId(bookId);
		return book;
	}
}
