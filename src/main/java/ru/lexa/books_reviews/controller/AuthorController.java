package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.controller.dto.BookRequestDTO;

import javax.validation.Valid;

@RequestMapping("/api/authors")
public interface AuthorController {
	@ApiOperation(value = "Добавить нового автора.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	BookDTO createAuthor(@RequestBody BookRequestDTO dto);
}
