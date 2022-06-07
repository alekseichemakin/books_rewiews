package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.author.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorRequestDTO;
import ru.lexa.books_reviews.controller.dto.author.AuthorResponseDTO;
import ru.lexa.books_reviews.domain.AuthorDomain;

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
	@Mapping(target = "filmIds", ignore = true)
	@Mapping(target = "bookIds", ignore = true)
	AuthorResponseDTO authorToDto(AuthorDomain author);
}
