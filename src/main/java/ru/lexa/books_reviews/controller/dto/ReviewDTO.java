package ru.lexa.books_reviews.controller.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private long id;

	private String text;

	private int rating;

	private long book_id;
}
