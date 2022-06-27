package ru.lexa.books_reviews.controller.mapper;

import controller.dto.author.AuthorDTO;
import controller.dto.author.AuthorRequestDTO;
import controller.dto.author.AuthorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер dto и domain автора
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {
	/**
	 * Преобразовывает domain автора в dto автора
	 *
	 * @param dto - dto автора
	 * @return - domain автора
	 */
	@Mapping(target = "avgRating", ignore = true)
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "id", source = "dto.id")
	AuthorDomain dtoToAuthor(AuthorDTO dto);

	/**
	 * Преобразовывает dto автора в domain автора
	 *
	 * @param dto - dto автора
	 * @return - domain автора
	 */
	@Mapping(target = "avgRating", ignore = true)
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "id", ignore = true)
	AuthorDomain dtoToAuthor(AuthorRequestDTO dto);

	/**
	 * Преобразовывает domain автора в dto автора
	 *
	 * @param author - domain автора
	 * @return - dto автора
	 */
	@Mapping(source = "films", target = "filmIds", qualifiedByName = "setFilmIds")
	@Mapping(source = "books", target = "bookIds", qualifiedByName = "setBookIds")
	AuthorResponseDTO authorToDto(AuthorDomain author);

	@Named("setBookIds")
	default List<Long> setBooksIds(List<Book> books) {
		return books.stream().map(Book::getId).collect(Collectors.toList());
	}

	@Named("setFilmIds")
	default List<Long> setFilmsIds(Collection<Film> films) {
		return films.stream().map(Film::getId).collect(Collectors.toList());
	}
}
