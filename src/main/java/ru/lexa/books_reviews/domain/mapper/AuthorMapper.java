package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.AuthorDTO;
import ru.lexa.books_reviews.controller.dto.AuthorRequestDTO;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
	@Mapping(target = "id", source = "dto.id")
	Author dtoToAuthor(AuthorDTO dto, Collection<Book> books, Collection<Film> films);

	Author dtoToAuthor(AuthorRequestDTO dto, Collection<Book> books, Collection<Film> films);

	AuthorDTO authorToDto(Author author);
}
