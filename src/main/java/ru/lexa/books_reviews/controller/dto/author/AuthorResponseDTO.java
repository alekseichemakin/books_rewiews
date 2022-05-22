package ru.lexa.books_reviews.controller.dto.author;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AuthorResponseDTO extends AuthorDTO {

	@ApiModelProperty(value = "Id книг.")
	private List<Long> bookIds;

	@ApiModelProperty(value = "Id фильмов.")
	private List<Long> filmIds;
}
