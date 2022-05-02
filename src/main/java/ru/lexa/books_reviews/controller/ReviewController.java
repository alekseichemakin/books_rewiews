package ru.lexa.books_reviews.controller;

import org.springframework.web.bind.annotation.RequestBody;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;

import javax.validation.Valid;
import java.util.Collection;

public interface ReviewController {
    ReviewDTO createReview(@Valid ReviewDTO dto);

    Collection<ReviewDTO> readAll();

    ReviewDTO readReview(long id);

    ReviewDTO updateReview(@Valid ReviewDTO dto, long id);

    void deleteReview(long id);
}
