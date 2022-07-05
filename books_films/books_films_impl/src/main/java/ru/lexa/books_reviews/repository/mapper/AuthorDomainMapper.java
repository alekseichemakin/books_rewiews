package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.AuthorBook;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

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
	@Mapping(target = "avgRating", ignore = true)
	@Mapping(source = "books", target = "books", qualifiedByName = "setBooksToDomain")
	AuthorDomain authorToDomain(Author author);

	@Named("setBooksToDomain")
	default List<Book> setBooksToDomain(List<AuthorBook> books) {
		return books.stream()
				.map(AuthorBook::getBook)
				.toList();
	}
}
