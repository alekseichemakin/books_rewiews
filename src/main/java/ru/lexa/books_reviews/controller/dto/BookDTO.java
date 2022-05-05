package ru.lexa.books_reviews.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookDTO {
	@ApiModelProperty(value = "Название книги.", example = "Book Name")
	private String name;

	@ApiModelProperty(value = "Описание книги.", example = "Book Description")
	private String description;

	@ApiModelProperty(value = "Автор книги.", example = "Book Author")
	private String author;
}
