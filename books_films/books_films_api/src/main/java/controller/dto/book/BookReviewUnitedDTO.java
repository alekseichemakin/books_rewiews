package controller.dto.book;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;

@Data
public class BookReviewUnitedDTO {
    @ApiModelProperty(value = "DTO запрашиваемой книги.")
    BookRequestDTO bookRequestDTO;

    @ApiModelProperty(value = "DTO запрашиваемого отзыва.")
    BookReviewRequestDTO bookReviewRequestDTO;
}
