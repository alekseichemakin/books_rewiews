package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.book.BookRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
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
	 * @param author - автор книги
	 * @param review - отзывы книги
	 * @param films - экранизаци книги
	 * @return - сущность книги
	 */
	@Mapping(target = "averageRating", ignore = true)
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "review", source = "review")
	@Mapping(target = "films", source = "films")
	Book dtoToBook(BookDTO dto, Author author, Collection<Review> review, Collection<Film> films);

	/**
	 * Преобразовывает dto книги в сущность книги
	 *
	 * @param dto - dto книги
	 * @param author - автор книги
	 * @param review - отзывы книги
	 * @param films - экранизаци книги
	 * @return - сущность книги
	 */
	@Mapping(target = "averageRating", ignore = true)
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "review", source = "review")
	@Mapping(target = "films", source = "films")
	@Mapping(target = "id", ignore = true)
	Book dtoToBook(BookRequestDTO dto, Author author, Collection<Review> review, Collection<Film> films);

	/**
	 * Преобразовывает сущность книги в dto книги
	 *
	 * @param book - сущность книги
	 * @param reviewCount - колличество отзывов
	 * @return - dto книги
	 */
	@Mapping(target = "authorId", source = "book.author.id")
	BookResponseDTO bookToDto(Book book, int reviewCount);
}
