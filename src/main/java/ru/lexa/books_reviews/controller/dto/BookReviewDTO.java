package ru.lexa.books_reviews.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookReviewDTO extends BookReviewRequestDTO {
	private long id;
}
