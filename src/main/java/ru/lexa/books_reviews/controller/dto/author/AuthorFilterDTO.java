package ru.lexa.books_reviews.controller.dto.author;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorFilterDTO {

	@ApiModelProperty(value = "Имя автора.", example = "Author")
	private String author;

	@ApiModelProperty(value = "Название книги.", example = "Book Name")
	private String book;

	@ApiModelProperty(value = "Название фильма.", example = "Film Name")
	private String film;

	@ApiModelProperty(value = "Наибольший ретинг книги", example = "5")
	private Double maxRating;

	@ApiModelProperty(value = "Страница", example = "0")
	private Integer page;

	@ApiModelProperty(value = "Размер страницы", example = "5")
	private Integer pageSize;

}
