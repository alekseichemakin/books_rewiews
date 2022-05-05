package ru.lexa.books_reviews.controller.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private String text;

	private int rating;

	private long book_id;
}
