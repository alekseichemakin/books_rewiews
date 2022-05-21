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
import java.util.List;
import java.util.Set;

/**
 * Маппер книг
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

	/**
	 * Преобразовывает dto книги в сущность книги
	 *
	 * @param dto    - dto книги
	 * @param authors - автор книги
	 * @param review - отзывы книги
	 * @param films  - экранизаци книги
	 * @return - сущность книги
	 */
	@Mapping(target = "authors", source = "authors")
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "review", source = "review")
	@Mapping(target = "films", source = "films")
	Book dtoToBook(BookDTO dto, Collection<Author> authors, Collection<Review> review, Collection<Film> films);

	/**
	 * Преобразовывает dto книги в сущность книги
	 *
	 * @param dto    - dto книги
	 * @param authors - автор книги
	 * @param review - отзывы книги
	 * @param films  - экранизаци книги
	 * @return - сущность книги
	 */
	@Mapping(target = "authors", source = "authors")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "review", source = "review")
	@Mapping(target = "films", source = "films")
	@Mapping(target = "id", ignore = true)
	Book dtoToBook(BookRequestDTO dto, Collection<Author> authors, Collection<Review> review, Collection<Film> films);

	//TODO add comments

	/**
	 * Преобразовывает сущность книги в dto книги
	 *
	 * @param book        - сущность книги
	 * @param reviewCount - колличество отзывов
	 * @return - dto книги
	 */
	@Mapping(target = "authorIds", source = "authorIds")
	@Mapping(target = "averageRating", source = "avgRating")
	BookResponseDTO bookToDto(Book book, int reviewCount, double avgRating, List<Long> authorIds);
}
