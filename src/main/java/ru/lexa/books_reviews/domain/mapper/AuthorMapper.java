package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorResponseDTO;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.Collection;
import java.util.List;

/**
 * Маппер автора
 */
//TODO improve description
@Mapper(componentModel = "spring")
public interface AuthorMapper {
	/**
	 * Преобразовывает dto автора в сущность автора
	 *
	 * @param dto   - dto автора
	 * @param books - книги автора
	 * @param films - фильмы автора
	 * @return - сущность автора
	 */
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "id", source = "dto.id")
	Author dtoToAuthor(AuthorDTO dto);

	/**
	 * Преобразовывает dto автора в сущность автора
	 *
	 * @param dto   - dto автора
	 * @return - сущность автора
	 */
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "id", ignore = true)
	Author dtoToAuthor(AuthorRequestDTO dto);

	/**
	 * Преобразовывает сущность автора в dto автора
	 *
	 * @param author - сущность автора
	 * @return - dto автора
	 */
	@Mapping(target = "bookIds", source = "bookIds")
	@Mapping(target = "filmIds", source = "filmIds")
	AuthorResponseDTO authorToDto(Author author, List<Long> bookIds, List<Long> filmIds);
}
