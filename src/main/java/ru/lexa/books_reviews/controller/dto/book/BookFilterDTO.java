package ru.lexa.books_reviews.controller.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для получения фильтров запроса
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFilterDTO {
	@ApiModelProperty(value = "Название книги.", example = "Book Name")
	private String name;

	@ApiModelProperty(value = "Описание книги.", example = "Book Description")
	private String description;

	@ApiModelProperty(value = "Имя автора книги.", example = "Book Author")
	private String author;

	@ApiModelProperty(value = "Текст отзыва к книге.", example = "Book Review Text")
	private String reviewText;

	@ApiModelProperty(value = "Наибольший ретинг книги", example = "5")
	private Double lessThenRating;

	@ApiModelProperty(value = "Страница", example = "0")
	private Integer page;

	@ApiModelProperty(value = "Размер страницы", example = "5")
	private Integer pageSize;
}
