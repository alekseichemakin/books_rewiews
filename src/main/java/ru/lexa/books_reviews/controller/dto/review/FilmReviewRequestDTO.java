package ru.lexa.books_reviews.controller.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FilmReviewRequestDTO {
	@ApiModelProperty(value = "Текст отзыва.", example = "Review Text")
	private String text;

	@ApiModelProperty(value = "Оценка фильма.", example = "5")
	private int rating;

	@ApiModelProperty(value = "Id фильма", example = "0")
	private long filmId;
}
