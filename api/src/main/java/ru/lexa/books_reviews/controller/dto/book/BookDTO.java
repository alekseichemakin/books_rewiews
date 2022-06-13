package ru.lexa.books_reviews.controller.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO книги
 */
@Data
public class BookDTO extends BookRequestDTO {
	@ApiModelProperty(value = "Id книги.", example = "0")
	private long id;
}
