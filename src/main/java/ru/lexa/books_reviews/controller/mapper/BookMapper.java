package ru.lexa.books_reviews.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.book.BookDTO;
import ru.lexa.books_reviews.controller.dto.book.BookRequestDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.domain.BookDomain;

/**
 * Маппер книг
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

	/**
	 * Преобразовывает dto книги в сущность книги
	 *
	 * @param dto     - dto книги
	 * @param authors - автор книги
	 * @param review  - отзывы книги
	 * @param films   - экранизаци книги
	 * @return - сущность книги
	 */
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "reviewCount", ignore = true)
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "averageRating", ignore = true)
	@Mapping(target = "authors", ignore = true)
	BookDomain dtoToBook(BookDTO dto);

	/**
	 * Преобразовывает dto книги в сущность книги
	 *
	 * @param dto     - dto книги
	 * @param authors - автор книги
	 * @param review  - отзывы книги
	 * @param films   - экранизаци книги
	 * @return - сущность книги
	 */
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "reviewCount", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "films", ignore = true)
	@Mapping(target = "averageRating", ignore = true)
	@Mapping(target = "authors", ignore = true)
	BookDomain dtoToBook(BookRequestDTO dto);


	/**
	 * Преобразовывает сущность книги в dto книги
	 *
	 * @param book        - сущность книги
	 * @param reviewCount - колличество отзывов
	 * @param avgRating   - срелний ретйтинг
	 * @param authorIds   - Id авторов
	 * @return - dto книги
	 */
	BookResponseDTO bookToDto(BookDomain book);
}
