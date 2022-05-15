package ru.lexa.books_reviews.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;

@Mapper(componentModel = "spring")
public interface BookReviewMapper {
	@Mapping(target = "bookId", source = "review.book.id")
	BookReviewDTO reviewToDto(Review review);

	@Mapping(target = "film", ignore = true)
	@Mapping(target = "book", source = "book")
	@Mapping(target = "id", source = "dto.id")
	Review dtoToReview(BookReviewDTO dto, Book book);

	@Mapping(target = "film", ignore = true)
	@Mapping(target = "book", source = "book")
	@Mapping(target = "id", ignore = true)
	Review dtoToReview(BookReviewRequestDTO dto, Book book);
}
