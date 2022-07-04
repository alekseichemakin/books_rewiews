package ru.lexa.books_reviews.controller.implementation;

import controller.AuthorController;
import controller.dto.author.AuthorDTO;
import controller.dto.author.AuthorFilterDTO;
import controller.dto.author.AuthorRequestDTO;
import controller.dto.author.AuthorResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.mapper.AuthorMapper;
import ru.lexa.books_reviews.service.AuthorService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.AuthorController}
 */
@AllArgsConstructor
@RestController
public class AuthorControllerImpl implements AuthorController {

	private AuthorService authorService;

	private AuthorMapper authorMapper;

	@Override
	public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
		return authorMapper.authorToDto(authorService.create(authorMapper.dtoToAuthor(dto)));
	}

	@Override
	public Collection<AuthorResponseDTO> readAll(AuthorFilterDTO authorFilterDTO) {
		return authorService.readAll(authorFilterDTO).stream().map(authorMapper::authorToDto).collect(Collectors.toList());
	}

	@Override
	public AuthorResponseDTO readAuthor(long id) {
		return authorMapper.authorToDto(authorService.read(id));
	}

	@Override
	public AuthorResponseDTO updateAuthor(AuthorDTO dto) {
		return authorMapper.authorToDto(authorService.update(authorMapper.dtoToAuthor(dto)));
	}

	@Override
	public void deleteAuthor(long id) {
		authorService.delete(id);
	}

	@Override
	public Double getRating(long id) {
		return authorService.getAverageRating(id);
	}
}
