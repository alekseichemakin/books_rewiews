package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.repository.entity.Author;

/**
 * Маппер сущности и domain авторра
 */
@Mapper(componentModel = "spring")
public interface AuthorDomainMapper {

	/**
	 * Преобразовывает domain автора в сущность автора
	 *
	 * @param domain - domain автора
	 * @return - сущность автора
	 */
	Author domainToAuthor(AuthorDomain domain);

	/**
	 * Преобразовывает сущность автора в domain автора
	 *
	 * @param author - сущность автора
	 * @return - domain автора
	 */
	AuthorDomain authorToDomain(Author author);
}
