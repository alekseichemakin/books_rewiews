package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.book.BookRequestDTO;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;

import java.util.Collection;

/**
 * Маппер книг
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

	/**
	 * Преобразовывает dto книги в сущность книги
	 *
	 * @param dto - dto книги
	 * @return - сущность книги
	 */
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "name", source = "dto.name")
	Book dtoToBook(BookDTO dto, Author author, Collection<Review> review, Collection<Film> films);

	/**
	 * Преобразовывает dto книги в сущность книги
	 *
	 * @param dto - dto книги
	 * @return - сущность книги
	 */
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "id", ignore = true)
	Book dtoToBook(BookRequestDTO dto, Author author, Collection<Review> review, Collection<Film> films);

	/**
	 * Преобразовывает сущность книги в dto книги
	 *
	 * @param book - сущность книги
	 * @return - dto книги
	 */
	@Mapping(target = "authorId", source = "book.author.id")
	BookDTO bookToDto(Book book);
}
