package ru.lexa.books_reviews.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.repository.entity.Film;

@Mapper(componentModel = "spring")
public interface FilmDomainMapper {
	Film domainToFilm(FilmDomain domain);

	@Mapping(target = "bookId", ignore = true)
	FilmDomain filmToDomain(Film film);
}
