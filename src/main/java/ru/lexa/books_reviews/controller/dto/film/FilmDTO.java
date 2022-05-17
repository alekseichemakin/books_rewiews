package ru.lexa.books_reviews.controller.dto.film;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO автора {@link ru.lexa.books_reviews.repository.entity.Author}
 */
@Data
public class FilmDTO extends FilmRequestDTO {
	@ApiModelProperty(value = "Id фильма.")
	private long id;
}
