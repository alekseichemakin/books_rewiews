package ru.lexa.books_reviews.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для получения фльтров запроса
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFilterDTO {
	private String name;

	private String description;

	private String author;

	private String reviewText;
}
