package ru.lexa.books_reviews.controller.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO отзыва книги {@link ru.lexa.books_reviews.repository.entity.Review}
 */
@Data
public class BookReviewDTO extends BookReviewRequestDTO {
	@ApiModelProperty(value = "Id отзыва.")
	private long id;
}
