package ru.lexa.books_reviews.controller.dto.author;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO запроса автора {@link ru.lexa.books_reviews.repository.entity.Author}
 */
@Data
public class AuthorRequestDTO {
	@ApiModelProperty(value = "Имя автора.", example = "Author Name")
	private String name;
}
