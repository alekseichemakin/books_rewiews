package ru.lexa.books_reviews.controller.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO запроса отзыва книги
 */
@Data
public class BookReviewRequestDTO {
	@ApiModelProperty(value = "Текст отзыва.", example = "Review Text")
	private String text;

	@ApiModelProperty(value = "Оценка книги.", example = "5")
	private int rating;

	@ApiModelProperty(value = "Id книги", example = "0")
	private long bookId;
}
