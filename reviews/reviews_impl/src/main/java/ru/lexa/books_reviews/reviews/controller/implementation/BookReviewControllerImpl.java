package ru.lexa.books_reviews.reviews.controller.implementation;


import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.BookReviewController;
import ru.lexa.books_reviews.controller.dto.review.BookReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.ReviewFilterDTO;
import ru.lexa.books_reviews.reviews.controller.mapper.BookReviewMapper;
import ru.lexa.books_reviews.reviews.service.ReviewService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.BookReviewController}
 */
@AllArgsConstructor
@RestController
public class BookReviewControllerImpl implements BookReviewController {

	private ReviewService reviewService;

	private BookReviewMapper reviewMapper;

	@RabbitListener(queues = {"${queue.name.createBookReview}"})
	public void listenerCreateReview(@Payload BookReviewRequestDTO dto) {
		reviewService.create(reviewMapper.dtoToReview(dto));
	}

	@Override
	public BookReviewDTO createReview(BookReviewRequestDTO dto) {
		return reviewMapper.reviewToDto(reviewService.create(reviewMapper.dtoToReview(dto)));
	}

	@Override
	public Collection<BookReviewDTO> readAll(String text, Long bookId) {
		ReviewFilterDTO filterDTO = new ReviewFilterDTO(text, bookId);
		return reviewService.readAllBooksReviews(filterDTO).stream()
				.map(review -> reviewMapper.reviewToDto(review))
				.collect(Collectors.toList());
	}

	@Override
	public BookReviewDTO readReview(long id) {
		return reviewMapper.reviewToDto(reviewService.read(id));
	}

	@Override
	public BookReviewDTO updateReview(BookReviewDTO dto) {
		return reviewMapper.reviewToDto(reviewService.update(reviewMapper.dtoToReview(dto)));}

	@Override
	public void deleteReview(long id) {
		reviewService.delete(id);
	}

	@Override
	public Double averageBookRating(long bookId) {
		return reviewService.getBookAverageRating(bookId);
	}
}
