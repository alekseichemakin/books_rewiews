package ru.lexa.books_reviews.controller.dto.film;
import lombok.Data;

import java.sql.Date;

@Data
public class FilmRequestDTO {
	private String name;

	private long bookId;

	private Date dateRelease;
}
