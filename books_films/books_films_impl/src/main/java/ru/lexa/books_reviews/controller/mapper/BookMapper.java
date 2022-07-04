package ru.lexa.books_reviews.controller.mapper;

import controller.dto.book.BookDTO;
import controller.dto.book.BookRequestDTO;
import controller.dto.book.BookResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер dto и domain книг
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * Преобразовывает dto книги в domain книги
     *
     * @param dto - dto книги
     * @return - domain книги
     */
    @Mapping(target = "reviewCount", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(source = "authorIds", target = "authors", qualifiedByName = "setAuthors")
    BookDomain dtoToBook(BookDTO dto);

    /**
     * Преобразовывает dto книги в domain книги
     *
     * @param dto - dto книги
     * @return - domain книги
     */
    @Mapping(target = "reviewCount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(source = "authorIds", target = "authors", qualifiedByName = "setAuthors")
    BookDomain dtoToBook(BookRequestDTO dto);


    /**
     * Преобразовывает domain книги в dto книги
     *
     * @param book - domain книги
     * @return - dto книги
     */
    @Mapping(source = "authors", target = "authorIds", qualifiedByName = "setAuthorIds")
    BookResponseDTO bookToDto(BookDomain book);

    @Named("setAuthorIds")
    default List<Long> setAuthorIds(List<Author> authors) {
        return authors.stream().map(Author::getId).collect(Collectors.toList());
    }

    @Named("setAuthors")
    default List<Author> setAuthors(List<Long> authorIds) {
        return authorIds.stream()
                .map(id -> {
                    Author a = new Author();
                    a.setId(id);
                    return a;
                })
                .collect(Collectors.toList());
    }
}
