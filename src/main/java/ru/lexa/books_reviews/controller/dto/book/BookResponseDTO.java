package ru.lexa.books_reviews.controller.dto.book;

import lombok.Data;

@Data
public class BookResponseDTO extends BookDTO {
	private int reviewCount;
	private double averageRating;
}
