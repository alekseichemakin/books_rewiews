package ru.lexa.books_reviews.controller.implementation;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.BookReviewController;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.domain.mapper.BookReviewMapper;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.BookReviewController}
 */
@AllArgsConstructor
@RestController
public class BookReviewControllerImpl implements BookReviewController {

	private ReviewService reviewService;

	private BookService bookService;

	private BookReviewMapper reviewMapper;

	@Override
	public BookReviewDTO createReview(BookReviewRequestDTO dto) {
		Book book = bookService.read(dto.getBookId());
		BookReviewDTO returnDto = reviewMapper.reviewToDto(reviewService.create(reviewMapper.dtoToReview(dto, book)));
		bookService.averageRating(dto.getBookId());
		return returnDto;
	}

	@Override
	public Collection<BookReviewDTO> readAll() {
		return reviewService.readAllBooksReviews().stream()
				.map(review -> reviewMapper.reviewToDto(review))
				.collect(Collectors.toList());
	}

	@Override
	public BookReviewDTO readReview(long id) {
		return reviewMapper.reviewToDto(reviewService.read(id));
	}

	@Override
	public BookReviewDTO updateReview(BookReviewDTO dto) {
		Book book = bookService.read(dto.getBookId());
		BookReviewDTO returnDto = reviewMapper.reviewToDto(reviewService.update(reviewMapper.dtoToReview(dto, book)));
		bookService.averageRating(dto.getBookId());
		return returnDto;
	}

	@Override
	public void deleteReview(long id) {
		long bookId = reviewService.read(id).getBook().getId();
		reviewService.delete(id);
		bookService.averageRating(bookId);
	}
}
