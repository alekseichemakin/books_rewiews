package controller.dto.author;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO запроса автора
 */
@Data
public class AuthorRequestDTO {
	@ApiModelProperty(value = "Имя автора.", example = "Author Name")
	private String name;
}
