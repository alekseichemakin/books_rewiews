package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.controller.dto.ReviewResponseDTO;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.BookService;

/**
 * Маппер отзывов
 */
@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
	@Autowired
	BookService bookService;

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @return - сущность отзыва
	 */
	@Mapping(target = "book", expression = "java(bookService.read(dto.getBook_id()))")
	public abstract Review dtoToReview(ReviewResponseDTO dto);

	/**
	 * Преобразовывает dto отзыва в сущность отзыва
	 *
	 * @param dto - dto отзыва
	 * @return - сущность отзыва
	 */
	@Mapping(target = "book", expression = "java(bookService.read(dto.getBook_id()))")
	public abstract Review dtoToReview(ReviewDTO dto);

	/**
	 * Преобразовывает сущность отзыва в dto отзыва
	 *
	 * @param review - сущность отзыва
	 * @return - dto отзыва
	 */
	@Mapping(target = "book_id", expression = "java(review.getBook().getId())")
	public abstract ReviewResponseDTO reviewToDto(Review review);



}
