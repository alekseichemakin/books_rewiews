package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.controller.dto.ReviewResponseDTO;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.BookService;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
	@Autowired
	BookService bookService;

	@Mapping(target = "book", expression = "java(bookService.read(dto.getBook_id()))")
	public abstract Review dtoToReview(ReviewResponseDTO dto);

	@Mapping(target = "book", expression = "java(bookService.read(dto.getBook_id()))")
	public abstract Review dtoToReview(ReviewDTO dto);

	@Mapping(target = "book_id", expression = "java(review.getBook().getId())")
	public abstract ReviewResponseDTO reviewToDto(Review review);
}
