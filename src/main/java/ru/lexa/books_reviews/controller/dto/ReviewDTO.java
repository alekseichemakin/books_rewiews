package ru.lexa.books_reviews.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewDTO {
	@ApiModelProperty(value = "Текст отзыва.", example = "Review Text")
	private String text;

	@ApiModelProperty(value = "Оценка книги.", example = "5")
	private int rating;

	@ApiModelProperty(value = "Id книги", example = "0")
	private long book_id;
}
