package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.controller.dto.BookResponseDTO;
import ru.lexa.books_reviews.repository.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
	Book dtoToBook(BookResponseDTO dto);

	Book dtoToBook(BookDTO dto);

	BookResponseDTO bookToDto(Book book);
}
