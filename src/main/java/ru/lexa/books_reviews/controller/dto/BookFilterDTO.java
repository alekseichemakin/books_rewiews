package ru.lexa.books_reviews.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для получения фильтров запроса
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//TODO добавить анноташек
public class BookFilterDTO {
	private String name;

	private String description;

	private String author;

	private String reviewText;

	//TODO добавить минимальный рейтинг, участвующий в выборке
}
