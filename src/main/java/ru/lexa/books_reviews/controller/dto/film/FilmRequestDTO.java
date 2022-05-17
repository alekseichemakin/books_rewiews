package ru.lexa.books_reviews.controller.dto.film;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class FilmRequestDTO {
	@ApiModelProperty(value = "Название фильма.", example = "Film Name")
	private String name;

	@ApiModelProperty(value = "Id экранизированной книги.", example = "0")
	private long bookId;

	@ApiModelProperty(value = "Дата экранизации фильма.", example = "01-01-2022")
	private Date dateRelease;
}
