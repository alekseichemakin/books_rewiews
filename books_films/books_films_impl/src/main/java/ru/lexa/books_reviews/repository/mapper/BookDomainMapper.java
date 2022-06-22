package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.entity.Book;

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
	BookDomain bookToDomain(Book book);
}
