package ru.lexa.books_reviews.reviews.controller.implementation;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.FilmReviewController;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.FilmReviewRequestDTO;
import ru.lexa.books_reviews.reviews.controller.mapper.FilmReviewMapper;
import ru.lexa.books_reviews.reviews.service.ReviewService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.FilmReviewController}
 */
@AllArgsConstructor
@RestController
public class FilmReviewControllerImpl implements FilmReviewController {

    private ReviewService reviewService;

    private FilmReviewMapper reviewMapper;

    @RabbitListener(queues = {"${queue.name.createFilmReview}"})
    public void listenerCreateReview(@Payload FilmReviewRequestDTO dto) {
        reviewService.create(reviewMapper.dtoToReview(dto));
    }

    @Override
    public FilmReviewDTO createReview(FilmReviewRequestDTO dto) {
        return reviewMapper.reviewToDto(reviewService.create(reviewMapper.dtoToReview(dto)));
    }

    @Override
    public Collection<FilmReviewDTO> readAll() {
        return reviewService.readAllFilmsReviews().stream()
                .map(review -> reviewMapper.reviewToDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public FilmReviewDTO readReview(long id) {
        return reviewMapper.reviewToDto(reviewService.read(id));
    }

    @Override
    public FilmReviewDTO updateReview(FilmReviewDTO dto) {
        return reviewMapper.reviewToDto(reviewService.update(reviewMapper.dtoToReview(dto)));
    }

    @Override
    public void deleteReview(long id) {
        reviewService.delete(id);
    }
}
