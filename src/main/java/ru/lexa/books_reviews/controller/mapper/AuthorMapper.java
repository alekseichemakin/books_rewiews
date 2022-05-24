package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorResponseDTO;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;

import java.util.Collection;
import java.util.List;

/**
 * Маппер автора
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {
	/**
	 * Преобразовывает dto автора в сущность автора
	 *
	 * @param dto   - dto автора
	 *
	 * @return - сущность автора
	 */
	@Mapping(target = "filmIds", ignore = true)
	@Mapping(target = "bookIds", ignore = true)
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "id", source = "dto.id")
	AuthorDomain dtoToAuthor(AuthorDTO dto);

	/**
	 * Преобразовывает dto автора в сущность автора
	 *
	 * @param dto   - dto автора
	 * @return - сущность автора
	 */
	@Mapping(target = "filmIds", ignore = true)
	@Mapping(target = "bookIds", ignore = true)
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "books", ignore = true)
	@Mapping(target = "id", ignore = true)
	AuthorDomain dtoToAuthor(AuthorRequestDTO dto);

	/**
	 * Преобразовывает сущность автора в dto автора
	 *
	 * @param author - сущность автора
	 * @return - dto автора
	 */
	AuthorResponseDTO authorToDto(AuthorDomain author);
}
