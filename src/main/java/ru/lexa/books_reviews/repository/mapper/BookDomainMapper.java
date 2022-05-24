package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.entity.Book;

@Mapper(componentModel = "spring")
public interface BookDomainMapper {
	Book domainToBook(BookDomain domain);

	@Mapping(target = "reviewCount", ignore = true)
	@Mapping(target = "averageRating", ignore = true)
	@Mapping(target = "authorIds", ignore = true)
	BookDomain bookToDomain(Book book);
}
