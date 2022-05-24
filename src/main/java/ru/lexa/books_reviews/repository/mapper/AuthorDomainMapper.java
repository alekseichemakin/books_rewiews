package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.repository.entity.Author;

@Mapper(componentModel = "spring")
public interface AuthorDomainMapper {

	Author domainToAuthor(AuthorDomain domain);

	@Mapping(target = "filmIds", ignore = true)
	@Mapping(target = "bookIds", ignore = true)
	AuthorDomain authorToDomain(Author author);
}
