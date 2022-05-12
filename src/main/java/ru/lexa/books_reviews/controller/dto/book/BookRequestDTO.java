package ru.lexa.books_reviews.controller.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Author;

/**
 * DTO книги {@link ru.lexa.books_reviews.repository.entity.Book}
 */
@Data
public class BookRequestDTO {
	@ApiModelProperty(value = "Название книги.", example = "Book Name")
	private String name;

	@ApiModelProperty(value = "Описание книги.", example = "Book Description")
	private String description;

	@ApiModelProperty(value = "ID автора книги.", example = "0")
	private long authorId;

	//TODO добавить среднюю оценку по отзывам
	//TODO добавить количество отзывов
}
