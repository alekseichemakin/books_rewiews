package ru.lexa.books_reviews.controller.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для получения фильтров запроса
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFilterDTO {
    @ApiModelProperty(value = "Текст отзыва.", example = "Review Text")
    String text;

    @ApiModelProperty(value = "Id книги.", example = "0")
    Long bookId;
}
