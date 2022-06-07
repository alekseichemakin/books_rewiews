package ru.lexa.books_reviews.controller.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO ответа книги {@link ru.lexa.books_reviews.repository.entity.Book}
 */
@Data
public class BookResponseDTO extends BookDTO {
	@ApiModelProperty(value = "Колличество отзывов к книге.")
	private int reviewCount;

	@ApiModelProperty(value = "Средний рейтинг книги.")
	private double averageRating;
}
