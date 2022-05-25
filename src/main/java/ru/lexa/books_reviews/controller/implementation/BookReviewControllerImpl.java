package ru.lexa.books_reviews.controller.implementation;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.BookReviewController;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.controller.mapper.BookMapper;
import ru.lexa.books_reviews.controller.mapper.BookReviewMapper;
import ru.lexa.books_reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
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

	private BookDomainMapper bookDomainMapper;


	@Override
	public BookReviewDTO createReview(BookReviewRequestDTO dto) {
		ReviewDomain reviewDomain = reviewMapper.dtoToReview(dto);
		reviewDomain.setBook(bookDomainMapper.domainToBook(bookService.read(dto.getBookId())));
		return reviewMapper.reviewToDto(reviewDomain);
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
		ReviewDomain reviewDomain = reviewMapper.dtoToReview(dto);
		reviewDomain.setBook(bookDomainMapper.domainToBook(bookService.read(dto.getBookId())));
		return reviewMapper.reviewToDto(reviewDomain);}

	@Override
	public void deleteReview(long id) {
		reviewService.delete(id);
	}
}
