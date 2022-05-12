package ru.lexa.books_reviews.controller.dto.review;

import lombok.Data;

/**
 * DTO ответа отзыва {@link ru.lexa.books_reviews.repository.entity.Review}
 */
@Data
public class ReviewResponseDTO extends ReviewDTO{
	private long id;
}
