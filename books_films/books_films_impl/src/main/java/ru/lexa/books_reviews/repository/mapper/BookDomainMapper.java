package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.AuthorBook;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер dto и сущности книг
 */
@Mapper(componentModel = "spring")
public interface BookDomainMapper {
	/**
	 * Преобразовывает domain книги в сущность книги
	 *
	 * @param domain - domain книги
	 * @return - сущность книги
	 */
	Book domainToBook(BookDomain domain);

	/**
	 * Преобразовывает сущность книги в domain книги
	 *
	 * @param book - сущность книги
	 * @return - domain книги
	 */
	@Mapping(target = "reviewCount", ignore = true)
	@Mapping(target = "averageRating", ignore = true)
	@Mapping(source = "authors", target = "authors", qualifiedByName = "setAuthorsToDomain")
	BookDomain bookToDomain(Book book);

	@Named("setAuthorsToDomain")
	default List<Author> setAuthorsToDomain(List<AuthorBook> authorBooks) {
		return authorBooks.stream()
				.map(AuthorBook::getAuthor)
				.collect(Collectors.toList());
	}
}
