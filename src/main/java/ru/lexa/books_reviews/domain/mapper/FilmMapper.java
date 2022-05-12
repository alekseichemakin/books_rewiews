package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.film.FilmDTO;
import ru.lexa.books_reviews.controller.dto.film.FilmRequestDTO;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

@Mapper(componentModel = "spring")
public interface FilmMapper {
	@Mapping(target = "authorId", source = "author.id")
	@Mapping(target = "bookId", source = "book.id")
	FilmDTO filmToDto(Film film);


	@Mapping(target = "dateRelease", source = "dto.dateRelease")
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "name", source = "dto.name")
	Film dtoToFilm(FilmDTO dto, Author author, Book book);

	@Mapping(target = "dateRelease", source = "dto.dateRelease")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "id", ignore = true)
	Film dtoToFilm(FilmRequestDTO dto, Author author, Book book);
}
