package ru.lexa.books_reviews.controller.dto.author;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO автора {@link ru.lexa.books_reviews.repository.entity.Author}
 */
@Data
public class AuthorDTO extends AuthorRequestDTO {
	@ApiModelProperty(value = "Id автора.")
	private long id;
	//TODO add books,films IDs
}
