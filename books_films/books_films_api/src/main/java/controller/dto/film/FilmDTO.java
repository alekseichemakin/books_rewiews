package controller.dto.film;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO автора
 */
@Data
public class FilmDTO extends FilmRequestDTO {
	@ApiModelProperty(value = "Id фильма.")
	private long id;
}
