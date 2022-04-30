package ru.lexa.books_reviews.controller.dto;

import lombok.Data;

@Data
public class BookDTO {
	private Long id;

	private String name;

	private String description;

	private String author;
}
