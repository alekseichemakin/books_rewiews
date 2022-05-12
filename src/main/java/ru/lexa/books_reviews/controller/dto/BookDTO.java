package ru.lexa.books_reviews.controller.dto;

import lombok.Data;

/**
 * DTO ответа книги {@link ru.lexa.books_reviews.repository.entity.Book}
 */
@Data
public class BookDTO extends BookRequestDTO {
	private long id;
}
