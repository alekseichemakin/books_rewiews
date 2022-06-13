package ru.lexa.books_reviews.controller.dto.author;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO автора
 */
@Data
public class AuthorDTO extends AuthorRequestDTO {
	@ApiModelProperty(value = "Id автора.")
	private long id;
}
