package ru.lexa.books_reviews.reviews.integration;

import controller.dto.book.BookResponseDTO;
import controller.dto.film.FilmDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "books-films")
public interface BookFilmClient {
    @GetMapping("/api/books/{id}")
    BookResponseDTO readBook(@PathVariable("id") long id);

    @GetMapping("/api/films/{id}")
    FilmDTO readFilm(@PathVariable("id") long id);
}
